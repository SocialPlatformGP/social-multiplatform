package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.PrivacyOptions
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.AuthError
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Result.Companion.failure
import com.gp.socialapp.util.Result.Companion.success
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import kotlinx.serialization.json.put


class AuthenticationRemoteDataSourceImpl(
    private val httpClient: HttpClient,
    private val supabaseClient: SupabaseClient
) : AuthenticationRemoteDataSource {

    private val sessionStatusFlow = supabaseClient.auth.sessionStatus
    override fun signInWithOAuth(provider: OAuthProvider): Flow<Result<User, AuthError.SignInWithOAuth>> =
        flow {
            emit(Result.Loading)
            try {
                supabaseClient.auth.signInWith(provider) {
                    scopes.addAll(listOf("email", "profile"))
                }
                sessionStatusFlow.collect { session ->
                    when (session) {
                        is SessionStatus.Authenticated -> {
                            getSignedInUser().let { result ->
                                when (result) {
                                    is Result.Success -> emit(success(result.data))
                                    is Result.Error -> emit(error(AuthError.SignInWithOAuth.CANT_GET_USER_DATA))
                                    else -> Unit
                                }
                            }
                        }

                        SessionStatus.LoadingFromStorage -> {
                            emit(Result.Loading)
                        }

                        SessionStatus.NetworkError -> {
                            emit(error(AuthError.SignInWithOAuth.NETWORK_ERROR))
                        }

                        is SessionStatus.NotAuthenticated -> {
                            emit(error(AuthError.SignInWithOAuth.NOT_AUTHENTICATED))
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(error(AuthError.SignInWithOAuth.NETWORK_ERROR))
            }
        }

    private suspend fun deleteRemoteUser(userId: String): Result<Unit, AuthError.DeleteUser> {
        return try {
            val response = httpClient.post {
                endPoint("deleteUser")
                setBody(userId)
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val error = response.body<AuthError.DeleteUser>()
                error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(AuthError.DeleteUser.SERVER_ERROR)
        }
    }

    override fun signInWithEmail(
        email: String, password: String
    ): Flow<Result<User, AuthError.SignInWithEmail>> = flow {
        emit(Result.Loading)
        try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            sessionStatusFlow.collect {
                when (it) {
                    is SessionStatus.Authenticated -> {
                        getSignedInUser().let {
                            when (it) {
                                is Result.Success -> emit(success(it.data))
                                is Result.Error -> emit(error(AuthError.SignInWithEmail.CANT_GET_USER_DATA))
                                else -> Unit
                            }
                        }
                    }

                    else -> Unit
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(error(AuthError.SignInWithEmail.SERVER_ERROR))
        }
    }

    override fun signUpWithEmail(
        email: String, password: String
    ): Flow<Result<User, AuthError.SignUpWithEmail>> = flow {
        emit(Result.Loading)
        try {
            supabaseClient.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            sessionStatusFlow.collect {
                when (it) {
                    is SessionStatus.Authenticated -> {
                        getSignedInUser().let {
                            when (it) {
                                is Result.Success -> emit(success(it.data))
                                is Result.Error -> emit(error(AuthError.SignUpWithEmail.CANT_GET_USER_DATA))
                                else -> Unit
                            }
                        }
                    }

                    else -> Unit
                }
            }
        } catch (e: Exception) {
            emit(error(AuthError.SignUpWithEmail.SERVER_ERROR))
        }
    }

    override suspend fun getSignedInUser(): Result<User, AuthError.GetSignedInUser> {
        val userInfo = supabaseClient.auth.sessionManager.loadSession()?.user
        return if (userInfo != null) {
            println("User Info: ${userInfo.userMetadata}")
            val isUserDataComplete =
                userInfo.userMetadata?.get("isUserDataComplete")?.jsonPrimitive?.booleanOrNull
                    ?: false
            val user = if (isUserDataComplete) {
                val id = userInfo.id
                val name =
                    userInfo.userMetadata?.get(UserData.NAME.value)?.jsonPrimitive?.contentOrNull
                        ?: ""
                val email = userInfo.email ?: ""
                val pfpUrl =
                    userInfo.userMetadata?.get(UserData.PROFILE_PICTURE_URL.value)?.jsonPrimitive?.contentOrNull
                        ?: ""
                val phoneNumber = userInfo.phone ?: ""
                val birthDate =
                    userInfo.userMetadata?.get(UserData.BIRTH_DATE.value)?.jsonPrimitive?.longOrNull
                        ?: 0L
                val bio =
                    userInfo.userMetadata?.get(UserData.BIO.value)?.jsonPrimitive?.contentOrNull
                        ?: ""
                val createdAt = userInfo.createdAt?.epochSeconds ?: 0L
                val isAdmin =
                    userInfo.userMetadata?.get(UserData.IS_ADMIN.value)?.jsonPrimitive?.booleanOrNull
                        ?: false
                User(
                    id = id,
                    name = name,
                    profilePictureURL = pfpUrl,
                    email = email,
                    phoneNumber = phoneNumber,
                    birthdate = birthDate,
                    bio = bio,
                    createdAt = createdAt,
                    isAdmin = isAdmin,
                    isDataComplete = isUserDataComplete
                )
            } else {
                val id = userInfo.id
                val email = userInfo.email ?: ""
                User(
                    id = id, email = email, isDataComplete = false
                )
            }
            success(user)
        } else {
            error(AuthError.GetSignedInUser.USER_NOT_FOUND)
        }
    }

    override suspend fun getUserSettings(): Result<UserSettings, AuthError.GetUserSettings> {
        val userInfo = supabaseClient.auth.sessionManager.loadSession()?.user
        return if (userInfo != null) {
            println("User Info: ${userInfo.userMetadata}")
            val isUserDataComplete =
                userInfo.userMetadata?.get("isUserDataComplete")?.jsonPrimitive?.booleanOrNull
                    ?: false
            val user = if (isUserDataComplete) {
                val id = userInfo.id
                val allowMessagesFrom =
                    userInfo.userMetadata?.get(UserData.ALLOW_MESSAGES_FROM.value)?.jsonPrimitive?.contentOrNull
                        ?: PrivacyOptions.EVERYONE.value
                val whoCanAddToGroups =
                    userInfo.userMetadata?.get(UserData.WHO_CAN_ADD_TO_GROUPS.value)?.jsonPrimitive?.contentOrNull
                        ?: PrivacyOptions.EVERYONE.value
                val isNotificationsAllowed =
                    userInfo.userMetadata?.get(UserData.ALLOW_NOTIFICATIONS.value)?.jsonPrimitive?.booleanOrNull
                        ?: true
                val isPostNotificationsAllowed =
                    userInfo.userMetadata?.get(UserData.ALLOW_POST_NOTIFICATIONS.value)?.jsonPrimitive?.booleanOrNull
                        ?: true
                val isChatNotificationsAllowed =
                    userInfo.userMetadata?.get(UserData.ALLOW_CHAT_NOTIFICATIONS.value)?.jsonPrimitive?.booleanOrNull
                        ?: true
                val isAssignmentsNotificationsAllowed =
                    userInfo.userMetadata?.get(UserData.ALLOW_ASSIGNMENTS_NOTIFICATIONS.value)?.jsonPrimitive?.booleanOrNull
                        ?: true
                val isCalendarNotificationsAllowed =
                    userInfo.userMetadata?.get(UserData.ALLOW_CALENDAR_NOTIFICATIONS.value)?.jsonPrimitive?.booleanOrNull
                        ?: true
                UserSettings(
                    userId = id,
                    allowMessagesFrom = allowMessagesFrom,
                    whoCanAddToGroups = whoCanAddToGroups,
                    isNotificationsAllowed = isNotificationsAllowed,
                    isPostNotificationsAllowed = isPostNotificationsAllowed,
                    isChatNotificationsAllowed = isChatNotificationsAllowed,
                    isAssignmentsNotificationsAllowed = isAssignmentsNotificationsAllowed,
                    isCalendarNotificationsAllowed = isCalendarNotificationsAllowed
                )
            } else {
                UserSettings()
            }
            success(user)
        } else {
            error(AuthError.GetUserSettings.USER_CANNOT_BE_FOUND)
        }
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Result<Unit, AuthError.ChangePassword> {
        return try {
            if (newPassword.isEmpty()) {
                failure(AuthError.ChangePassword.PASSWORD_CANNOT_BE_EMPTY)
            }
//            val user = supabaseClient.auth.retrieveUserForCurrentSession(updateSession = true)
//            TODO("Check if old password is correct")
            supabaseClient.auth.updateUser {
                password = newPassword
            }
            success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            failure(AuthError.ChangePassword.SERVER_ERROR)
        }
    }

    override suspend fun changeEmail(email: String): Result<Unit, AuthError.ChangeEmail> {
        return try {
            if (email.isEmpty()) {
                failure(AuthError.ChangeEmail.EMAIL_CANNOT_BE_EMPTY)
            }
            supabaseClient.auth.updateUser {
                this.email = email
            }
            success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            failure(AuthError.ChangeEmail.SERVER_ERROR)
        }
    }

    override suspend fun updateStringRemoteUserSetting(
        tag: String,
        value: String
    ): Result<Unit, AuthError.UpdateUserSetting> {
        return try {
            if (value.isEmpty()) {
                failure(AuthError.UpdateUserSetting.VALUES_CANNOT_BE_EMPTY)
            }
            if (tag.isEmpty()) {
                failure(AuthError.UpdateUserSetting.TAGS_CANNOT_BE_EMPTY)
            }
            supabaseClient.auth.updateUser {
                data {
                    put(tag, value)
                }
            }
            success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            failure(AuthError.UpdateUserSetting.SERVER_ERROR)
        }
    }

    override suspend fun updateBooleanRemoteUserSetting(
        tag: String,
        value: Boolean
    ): Result<Unit, AuthError.UpdateUserSetting> {
        return try {
            if (tag.isEmpty()) {
                failure(AuthError.UpdateUserSetting.TAGS_CANNOT_BE_EMPTY)
            }
            if (value) {
                failure(AuthError.UpdateUserSetting.VALUES_CANNOT_BE_EMPTY)
            }
            supabaseClient.auth.updateUser {
                data {
                    put(tag, value)
                }
            }
            success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            error(AuthError.UpdateUserSetting.SERVER_ERROR)
        }
    }

    override suspend fun logout(): Result<Unit, AuthError.Logout> {
        return try {
            supabaseClient.auth.signOut()
            success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            failure(AuthError.Logout.SERVER_ERROR)
        }
    }

    override suspend fun deleteAccount(userId: String): Result<Unit, AuthError.DeleteUser> {
        return try {
            if (userId.isEmpty()) {
                failure(AuthError.DeleteUser.USER_ID_CANNOT_BE_EMPTY)
            }
            supabaseClient.auth.admin.deleteUser(userId)
            deleteRemoteUser(userId)
        } catch (e: Exception) {
            e.printStackTrace()
            failure(AuthError.DeleteUser.SERVER_ERROR)
        }
    }


    override fun sendPasswordResetEmail(email: String): Flow<Result<Unit, AuthError.SendPasswordResetEmail>> {
        TODO("Not yet implemented")
    }
}

enum class UserData(val value: String) {
    NAME("name"), PROFILE_PICTURE_URL("profile_picture_url"), BIRTH_DATE("birth_date"), BIO("bio"), IS_ADMIN(
        "isAdmin"
    ),
    IS_DATA_COMPLETE("isUserDataComplete"), ALLOW_MESSAGES_FROM("allowMessagesFrom"), WHO_CAN_ADD_TO_GROUPS(
        "whoCanAddToGroups"
    ),
    ALLOW_NOTIFICATIONS("allowNotifications"), ALLOW_CHAT_NOTIFICATIONS("allowChatNotifications"), ALLOW_CALENDAR_NOTIFICATIONS(
        "allowCalendarNotifications"
    ),
    ALLOW_ASSIGNMENTS_NOTIFICATIONS("allowAssignmentsNotifications"), ALLOW_POST_NOTIFICATIONS("allowPostNotifications"),
}