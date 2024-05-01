package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.PrivacyOptions
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import kotlinx.serialization.json.put


class AuthenticationRemoteDataSourceImpl(
    private val supabaseClient: SupabaseClient
) : AuthenticationRemoteDataSource {

    private val sessionStatusFlow = supabaseClient.auth.sessionStatus
    override fun signInWithOAuth(provider: OAuthProvider): Flow<Result<User>> = flow {
        emit(Result.Loading)
        try {
            supabaseClient.auth.signInWith(provider) {
                scopes.addAll(listOf("email", "profile"))
            }
            sessionStatusFlow.collect { session ->
                when (session) {
                    is SessionStatus.Authenticated -> {
                        getSignedInUser().let { result ->
                            emit(result)
                        }
                    }

                    SessionStatus.LoadingFromStorage -> {
                        Napier.e("Loading from storage")
                        emit(Result.Loading)
                    }

                    SessionStatus.NetworkError -> {
                        Napier.e("Network Error")
                        emit(Result.Error("Network Error"))
                    }

                    is SessionStatus.NotAuthenticated -> {

                        emit(Result.Error("Not Authenticated"))
                    }
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Null"))
        }
    }

    override fun signInWithEmail(
        email: String, password: String
    ): Flow<Result<User>> = flow {
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
                            emit(it)
                        }
                    }

                    else -> Unit
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Null"))
        }
    }

    override fun signUpWithEmail(
        email: String, password: String
    ): Flow<Result<User>> = flow {
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
                            emit(it)
                        }
                    }

                    else -> Unit
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Null"))
        }
    }

    override suspend fun getSignedInUser(): Result<User> {
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
            Result.SuccessWithData(user)
        } else {
            Result.Error("User not found")
        }
    }

    override suspend fun getUserSettings(): Result<UserSettings> {
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
            Result.SuccessWithData(user)
        } else {
            Result.Error("User not found")
        }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): Result<Nothing> {
        return try {
//            val user = supabaseClient.auth.retrieveUserForCurrentSession(updateSession = true)
//            TODO("Check if old password is correct")
            supabaseClient.auth.updateUser {
                password = newPassword
            }
            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Null")
        }
    }

    override suspend fun changeEmail(email: String): Result<Nothing> {
        return try {
            supabaseClient.auth.updateUser {
                this.email = email
            }
            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Null")
        }
    }

    override suspend fun updateStringRemoteUserSetting(
        tag: String,
        value: String
    ): Result<Nothing> {
        return try {
            supabaseClient.auth.updateUser {
                data{
                    put(tag, value)
                }
            }
            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Null")
        }
    }

    override suspend fun updateBooleanRemoteUserSetting(
        tag: String,
        value: Boolean
    ): Result<Nothing> {
        return try {
            supabaseClient.auth.updateUser {
                data{
                    put(tag, value)
                }
            }
            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Null")
        }
    }

    override suspend fun logout(): Result<Nothing> {
        return try {
            supabaseClient.auth.signOut()
            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Null")
        }
    }

    override suspend fun deleteAccount(userId: String): Result<Nothing> {
        return try {
            supabaseClient.auth.admin.deleteUser(userId)
            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Null")
        }
    }


    override fun sendPasswordResetEmail(email: String): Flow<Result<Nothing>> {
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