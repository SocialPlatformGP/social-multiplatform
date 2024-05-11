package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.PrivacyOptions
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.data.auth.source.remote.model.requests.UpdateUserEndpoint
import com.gp.socialapp.data.auth.source.remote.model.requests.UpdateUserRequest
import com.gp.socialapp.data.chat.model.UserRooms
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.data.community.source.remote.model.request.CommunityRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.UserError
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

class UserRemoteDataSourceImpl(
    private val httpClient: HttpClient,
    private val supabaseClient: SupabaseClient
) : UserRemoteDataSource {
    override suspend fun updateUserInfo(user: User): Result<Unit, UserError> {
        return try {
            supabaseClient.auth.updateUser {
                phone = user.phoneNumber
                data {
                    put(UserData.NAME.value, user.name)
                    put(UserData.BIRTH_DATE.value, user.birthdate)
                    put(UserData.PROFILE_PICTURE_URL.value, user.profilePictureURL)
                    put(UserData.BIO.value, user.bio)
                    put(UserData.IS_DATA_COMPLETE.value, true)
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(UserError.SERVER_ERROR)
        }
    }

    override suspend fun updatePhoneNumber(
        userId: String,
        phoneNumber: String
    ): Result<Unit, UserError> {
        return try {
            supabaseClient.auth.updateUser {
                this.phone = phoneNumber
            }
            val result = updateRemoteUser(
                UpdateUserRequest.UpdatePhoneNumber(userId, phoneNumber),
                UpdateUserEndpoint.UpdatePhoneNumber
            )
            if (result is Result.Success) {
                Result.Success(Unit)
            } else {
                Result.Error(UserError.SERVER_ERROR)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(UserError.SERVER_ERROR)
        }
    }

    override suspend fun updateName(
        userId: String,
        name: String
    ): Result<Unit, UserError> {
        return try {
            supabaseClient.auth.updateUser {
                data {
                    put(UserData.NAME.value, name)
                }
            }
            val result = updateRemoteUser(
                UpdateUserRequest.UpdateName(userId, name),
                UpdateUserEndpoint.UpdateName
            )
            if (result is Result.Success) {
                Result.Success(Unit)
            } else {
                Result.Error(UserError.SERVER_ERROR)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(UserError.SERVER_ERROR)
        }
    }


    override fun fetchUsers(): Flow<Result<List<User>, UserError>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint("getAllUsers")
            }
            if (response.status == HttpStatusCode.OK) {
                val users = response.body<List<User>>()
                emit(Result.Success(users))
            } else {
                emit(Result.Error(UserError.SERVER_ERROR))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(UserError.SERVER_ERROR))
        }
    }


    override fun getUsersByIds(request: GetUsersByIdsRequest): Flow<Result<List<User>, UserError>> =
        flow {
            println("Request: $request")
            emit(Result.Loading)
            try {
                val response = httpClient.post {
                    endPoint("getUsersByIds")
                    setBody(request)
                }
                if (response.status == HttpStatusCode.OK) {
                    val users = response.body<List<User>>()
                    emit(Result.Success(users))
                } else {
                    val error = response.body<UserError>()
                    emit(Result.Error(error))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(UserError.SERVER_ERROR))
            }
        }

    override suspend fun uploadUserPfp(
        pfpByteArray: ByteArray,
        userId: String
    ): Result<String, UserError> {
        return try {
            val path = "${userId.first()}/$userId"
            val bucket = supabaseClient.storage.from("avatars")
            bucket.upload(path, pfpByteArray, upsert = true)
            val url = supabaseClient.storage.from("avatars").publicUrl(path)
            Result.Success(url)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(UserError.SERVER_ERROR)
        }
    }

    override suspend fun updateUserAvatar(
        avatarByteArray: ByteArray,
        userId: String
    ): Result<Unit, UserError> {
        return try {
            val result = uploadUserPfp(avatarByteArray, userId)
            if (result is Result.Success) {
                supabaseClient.auth.updateUser {
                    data {
                        put(UserData.PROFILE_PICTURE_URL.value, result.data)
                    }
                }
                val result2 = updateRemoteUser(
                    UpdateUserRequest.UpdateAvatarUrl(userId, result.data),
                    UpdateUserEndpoint.UpdateAvatarUrl
                )
                if (result2 is Result.Success) {
                    Result.Success(Unit)
                }
                else{
                    Result.Error(UserError.SERVER_ERROR)
                }
            } else {
                Result.Error(UserError.SERVER_ERROR)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(UserError.SERVER_ERROR)
        }
    }

    override suspend fun getUserSettings(): Result<UserSettings, UserError> {
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
            Result.Success(user)
        } else {
            Result.Error(UserError.SERVER_ERROR)
        }
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Result<Unit, UserError> {
        return try {
//            val user = supabaseClient.auth.retrieveUserForCurrentSession(updateSession = true)
//            TODO("Check if old password is correct")
            supabaseClient.auth.updateUser {
                password = newPassword
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(UserError.SERVER_ERROR)
        }
    }

    override suspend fun changeEmail(
        userId: String,
        email: String
    ): Result<Unit, UserError> {
        return try {
            supabaseClient.auth.updateUser {
                this.email = email
            }
            val result = updateRemoteUser(
                UpdateUserRequest.UpdateEmail(userId, email),
                UpdateUserEndpoint.UpdateEmail
            )
            if (result is Result.Success) {
                Result.Success(Unit)
            } else {
                Result.Error(UserError.SERVER_ERROR)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(UserError.SERVER_ERROR)
        }
    }

    override suspend fun updateStringRemoteUserSetting(
        userId: String,
        tag: String,
        value: String
    ): Result<Unit, UserError> {
        return try {
            supabaseClient.auth.updateUser {
                data {
                    put(tag, value)
                }
            }
            val request = when (tag) {
                UserData.ALLOW_MESSAGES_FROM.value -> UpdateUserRequest.UpdateAllowMessagesFrom(
                    userId,
                    value
                )

                UserData.WHO_CAN_ADD_TO_GROUPS.value -> UpdateUserRequest.UpdateWhoCanAddToGroups(
                    userId,
                    value
                )

                else -> return Result.Error(UserError.SERVER_ERROR)
            }
            val endpoint = when (tag) {
                UserData.ALLOW_MESSAGES_FROM.value -> UpdateUserEndpoint.UpdateAllowMessagesFrom
                UserData.WHO_CAN_ADD_TO_GROUPS.value -> UpdateUserEndpoint.UpdateWhoCanAddToGroups
                else -> return Result.Error(UserError.SERVER_ERROR)
            }
            val result = updateRemoteUser(
                request,
                endpoint
            )
            if (result is Result.Success) {
                Result.Success(Unit)
            } else {
                Result.Error(UserError.SERVER_ERROR)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(UserError.SERVER_ERROR)
        }
    }

    override suspend fun updateBooleanRemoteUserSetting(
        userId: String,
        tag: String,
        value: Boolean
    ): Result<Unit, UserError> {
        return try {
            supabaseClient.auth.updateUser {
                data {
                    put(tag, value)
                }
            }
            val request = when (tag) {
                UserData.ALLOW_NOTIFICATIONS.value -> UpdateUserRequest.UpdateIsNotificationsAllowed(
                    userId,
                    value
                )

                UserData.ALLOW_POST_NOTIFICATIONS.value -> UpdateUserRequest.UpdateIsPostNotificationsAllowed(
                    userId,
                    value
                )

                UserData.ALLOW_CHAT_NOTIFICATIONS.value -> UpdateUserRequest.UpdateIsChatNotificationsAllowed(
                    userId,
                    value
                )

                UserData.ALLOW_ASSIGNMENTS_NOTIFICATIONS.value -> UpdateUserRequest.UpdateIsAssignmentsNotificationsAllowed(
                    userId,
                    value
                )

                UserData.ALLOW_CALENDAR_NOTIFICATIONS.value -> UpdateUserRequest.UpdateIsCalendarNotificationsAllowed(
                    userId,
                    value
                )

                else -> return Result.Error(UserError.SERVER_ERROR)
            }
            val endpoint = when (tag) {
                UserData.ALLOW_NOTIFICATIONS.value -> UpdateUserEndpoint.UpdateIsNotificationsAllowed
                UserData.ALLOW_POST_NOTIFICATIONS.value -> UpdateUserEndpoint.UpdateIsPostNotificationsAllowed
                UserData.ALLOW_CHAT_NOTIFICATIONS.value -> UpdateUserEndpoint.UpdateIsChatNotificationsAllowed
                UserData.ALLOW_ASSIGNMENTS_NOTIFICATIONS.value -> UpdateUserEndpoint.UpdateIsAssignmentsNotificationsAllowed
                UserData.ALLOW_CALENDAR_NOTIFICATIONS.value -> UpdateUserEndpoint.UpdateIsCalendarNotificationsAllowed
                else -> return Result.Error(UserError.SERVER_ERROR)
            }
            updateRemoteUser(
                request,
                endpoint
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(UserError.SERVER_ERROR)
        }
    }

    override suspend fun createRemoteUser(user: User): Result<Unit, UserError> =
        try {
            val request = httpClient.post {
                endPoint("createUser")
                setBody(user)
            }
            if (request.status == HttpStatusCode.OK) {
                val userRoomsTable = supabaseClient.postgrest["user_rooms"]
                userRoomsTable.insert(
                    UserRooms(
                        userId = user.id,
                    )
                )
                Result.Success(Unit)
            } else {
                val error = request.body<UserError>()
                Result.Error(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(UserError.SERVER_ERROR)
        }

    override fun getUserCommunities(userId: String): Flow<Result<List<Community>, UserError>> =
        flow {
            emit(Result.Loading)
            try {
                val response = httpClient.post {
                    endPoint("getUserCommunities")
                    setBody(userId)
                }
                println(response)
                if (response.status == HttpStatusCode.OK) {
                    val communities = response.body<List<Community>>()
                    emit(Result.Success(communities))
                } else {
                    val error = response.body<UserError>()
                    emit(Result.Error(error))
                }

            } catch (e: Exception) {
                emit(Result.Error(UserError.SERVER_ERROR))
            }

        }

    override fun communityLogout(
        id: String,
        selectedCommunityId: String
    ): Flow<Result<List<Community>, UserError>> =
        flow {
            emit(Result.Loading)
            val request = CommunityRequest.LogoutCommunity(id, selectedCommunityId)
            try {
                val response = httpClient.post {
                    endPoint("communityLogout")
                    setBody(request)
                }
                if (response.status == HttpStatusCode.OK) {
                    val communities = response.body<List<Community>>()
                    emit(Result.Success(communities))
                } else {
                    val error = response.body<UserError>()
                    emit(Result.Error(error))
                }

            } catch (e: Exception) {
                emit(Result.Error(UserError.SERVER_ERROR))
            }
        }

    private suspend fun updateRemoteUser(
        request: UpdateUserRequest,
        endpoint: UpdateUserEndpoint
    ): Result<Unit, UserError> {
        return try {
            val response = httpClient.post {
                endPoint(endpoint.route)
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.Success(Unit)
            } else {
                Result.Error(UserError.SERVER_ERROR)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(UserError.SERVER_ERROR)
        }
    }

    override fun joinCommunity(
        id: String,
        code: String
    ): Flow<Result<List<Community>, UserError>> =
        flow {
            emit(Result.Loading)
            val request = CommunityRequest.JoinCommunity(id, code)
            try {
                println(request)
                val response = httpClient.post {
                    endPoint("/joinCommunity")
                    setBody(request)
                }
                println(response)
                if (response.status == HttpStatusCode.OK) {
                    val communities = response.body<List<Community>>()
                    emit(Result.Success(communities))
                } else {
                    val error = response.body<UserError>()
                    emit(Result.Error(error))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(UserError.SERVER_ERROR))
            }
        }

}