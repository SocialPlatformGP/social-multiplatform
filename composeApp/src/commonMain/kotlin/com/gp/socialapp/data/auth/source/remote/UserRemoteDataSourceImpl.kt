package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.PrivacyOptions
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.data.auth.source.remote.model.requests.UpdateUserEndpoint
import com.gp.socialapp.data.auth.source.remote.model.requests.UpdateUserRequest
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.data.community.source.remote.model.request.CommunityRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.DataSuccess
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Results
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
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
    override suspend fun updateUserInfo(user: User): Result<Nothing> {
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
            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun updatePhoneNumber(userId: String, phoneNumber: String): Result<Nothing> {
        return try {
            supabaseClient.auth.updateUser {
                this.phone = phoneNumber
            }
            updateRemoteUser(
                UpdateUserRequest.UpdatePhoneNumber(userId, phoneNumber),
                UpdateUserEndpoint.UpdatePhoneNumber
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Null")
        }
    }

    override suspend fun updateName(userId: String, name: String): Result<Nothing> {
        return try {
            supabaseClient.auth.updateUser {
                data {
                    put(UserData.NAME.value, name)
                }
            }
            updateRemoteUser(
                UpdateUserRequest.UpdateName(userId, name),
                UpdateUserEndpoint.UpdateName
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Null")
        }
    }


    override fun fetchUsers(): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint("getAllUsers")
            }
            if (response.status == HttpStatusCode.OK) {
                val users = response.body<List<User>>()
                emit(Result.SuccessWithData(users))
            } else {
                emit(Result.Error("An unknown error occurred ${response.status}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message ?: "An unknown error occurred"))
        }
    }


    override fun getUsersByIds(request: GetUsersByIdsRequest): Flow<Results<List<User>, DataError.Network>> =
        flow {
            println("Request: $request")
            emit(Results.Loading)
            try {
                val response = httpClient.post {
                    endPoint("getUsersByIds")
                    setBody(request)
                }
                if (response.status == HttpStatusCode.OK) {
                    val users = response.body<List<User>>()
                    emit(Results.Success(users))
                } else {
                    val error = response.body<DataError.Network>()
                    emit(Results.Failure(error))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
            }
        }

    override suspend fun uploadUserPfp(pfpByteArray: ByteArray, userId: String): Result<String> {
        return try {
            val path = "${userId.first()}/$userId"
            val bucket = supabaseClient.storage.from("avatars")
            bucket.upload(path, pfpByteArray, upsert = true)
            val url = supabaseClient.storage.from("avatars").publicUrl(path)
            Result.SuccessWithData(url)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun updateUserAvatar(avatarByteArray: ByteArray, userId: String): Result<Nothing> {
        return try{
            uploadUserPfp(avatarByteArray, userId).let { result ->
                if (result is Result.SuccessWithData) {
                    supabaseClient.auth.updateUser {
                        data {
                            put(UserData.PROFILE_PICTURE_URL.value, result.data)
                        }
                    }
                    updateRemoteUser(
                        UpdateUserRequest.UpdateAvatarUrl(userId, result.data),
                        UpdateUserEndpoint.UpdateAvatarUrl
                    )
                } else {
                    Result.Error("An error occurred while uploading the profile picture")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "An unknown error occurred")
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

    override suspend fun changeEmail(userId: String, email: String): Result<Nothing> {
        return try {
            supabaseClient.auth.updateUser {
                this.email = email
            }
            updateRemoteUser(
                UpdateUserRequest.UpdateEmail(userId, email),
                UpdateUserEndpoint.UpdateEmail
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Null")
        }
    }

    override suspend fun updateStringRemoteUserSetting(
        userId: String,
        tag: String,
        value: String
    ): Result<Nothing> {
        return try {
            supabaseClient.auth.updateUser {
                data{
                    put(tag, value)
                }
            }
            val request = when(tag){
                UserData.ALLOW_MESSAGES_FROM.value -> UpdateUserRequest.UpdateAllowMessagesFrom(userId, value)
                UserData.WHO_CAN_ADD_TO_GROUPS.value -> UpdateUserRequest.UpdateWhoCanAddToGroups(userId, value)
                else -> return Result.Error("Invalid tag")
            }
            val endpoint = when(tag){
                UserData.ALLOW_MESSAGES_FROM.value -> UpdateUserEndpoint.UpdateAllowMessagesFrom
                UserData.WHO_CAN_ADD_TO_GROUPS.value -> UpdateUserEndpoint.UpdateWhoCanAddToGroups
                else -> return Result.Error("Invalid tag")
            }
            updateRemoteUser(
                request,
                endpoint
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Null")
        }
    }

    override suspend fun updateBooleanRemoteUserSetting(
        userId: String,
        tag: String,
        value: Boolean
    ): Result<Nothing> {
        return try {
            supabaseClient.auth.updateUser {
                data{
                    put(tag, value)
                }
            }
            val request = when(tag){
                UserData.ALLOW_NOTIFICATIONS.value -> UpdateUserRequest.UpdateIsNotificationsAllowed(userId, value)
                UserData.ALLOW_POST_NOTIFICATIONS.value -> UpdateUserRequest.UpdateIsPostNotificationsAllowed(userId, value)
                UserData.ALLOW_CHAT_NOTIFICATIONS.value -> UpdateUserRequest.UpdateIsChatNotificationsAllowed(userId, value)
                UserData.ALLOW_ASSIGNMENTS_NOTIFICATIONS.value -> UpdateUserRequest.UpdateIsAssignmentsNotificationsAllowed(userId, value)
                UserData.ALLOW_CALENDAR_NOTIFICATIONS.value -> UpdateUserRequest.UpdateIsCalendarNotificationsAllowed(userId, value)
                else -> return Result.Error("Invalid tag")
            }
            val endpoint = when(tag){
                UserData.ALLOW_NOTIFICATIONS.value -> UpdateUserEndpoint.UpdateIsNotificationsAllowed
                UserData.ALLOW_POST_NOTIFICATIONS.value -> UpdateUserEndpoint.UpdateIsPostNotificationsAllowed
                UserData.ALLOW_CHAT_NOTIFICATIONS.value -> UpdateUserEndpoint.UpdateIsChatNotificationsAllowed
                UserData.ALLOW_ASSIGNMENTS_NOTIFICATIONS.value -> UpdateUserEndpoint.UpdateIsAssignmentsNotificationsAllowed
                UserData.ALLOW_CALENDAR_NOTIFICATIONS.value -> UpdateUserEndpoint.UpdateIsCalendarNotificationsAllowed
                else -> return Result.Error("Invalid tag")
            }
            updateRemoteUser(
                request,
                endpoint
            )
            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Null")
        }
    }

    override suspend fun createRemoteUser(user: User): Results<DataSuccess.User, DataError.Network> =
        try {
            val request = httpClient.post {
                endPoint("createUser")
                setBody(user)
            }
            if (request.status == HttpStatusCode.OK) {
                val message = request.body<DataSuccess.User>()
                Results.Success(message)
            } else {
                val error = request.body<DataError.Network>()
                Results.Failure(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }

    override fun getUserCommunities(userId: String): Flow<Results<List<Community>, DataError.Network>> =
        flow {
            emit(Results.Loading)
            try {
                val response = httpClient.post {
                    endPoint("getUserCommunities")
                    setBody(userId)
                }
                if (response.status == HttpStatusCode.OK) {
                    val communities = response.body<List<Community>>()
                    emit(Results.Success(communities))
                } else {
                    val error = response.body<DataError.Network>()
                    emit(Results.Failure(error))
                }

            } catch (e: Exception) {
                emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
            }

        }

    override fun communityLogout(
        id: String,
        selectedCommunityId: String
    ): Flow<Results<List<Community>, DataError.Network>> =
        flow {
            emit(Results.Loading)
            val request = CommunityRequest.LogoutCommunity(id, selectedCommunityId)
            try {
                val response = httpClient.post {
                    endPoint("communityLogout")
                    setBody(request)
                }
                if (response.status == HttpStatusCode.OK) {
                    val communities = response.body<List<Community>>()
                    emit(Results.Success(communities))
                } else {
                    val error = response.body<DataError.Network>()
                    emit(Results.Failure(error))
                }

            } catch (e: Exception) {
                emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
            }
        }
    private suspend fun updateRemoteUser(request: UpdateUserRequest, endpoint: UpdateUserEndpoint) : Result<Nothing>{
        return try{
            val response = httpClient.post {
                endPoint(endpoint.route)
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK){
                Result.Success
            } else {
                Result.Error("An unknown error occurred")
            }
        } catch(e: Exception){
            e.printStackTrace()
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }
    override fun joinCommunity(
        id: String,
        code: String
    ): Flow<Results<List<Community>, DataError.Network>> =
        flow {
            emit(Results.Loading)
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
                    emit(Results.Success(communities))
                } else {
                    val error = response.body<DataError.Network>()
                    emit(Results.Failure(error))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
            }
        }

}