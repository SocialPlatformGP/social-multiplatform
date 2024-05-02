package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorage
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.DataSuccess
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userRemoteSource: UserRemoteDataSource,
    private val localKeyValueStorage: AuthKeyValueStorage
) : UserRepository {
    override suspend fun updateUserInfo(user: User, pfpByteArray: ByteArray): Result<Nothing> {
        return if (pfpByteArray.isNotEmpty()) {
            println("Uploading profile picture")
            val result = userRemoteSource.uploadUserPfp(pfpByteArray, user.id)
            if (result is Result.SuccessWithData) {
                userRemoteSource.updateUserInfo(user.copy(profilePictureURL = result.data))
            } else {
                Result.Error("An error occurred while uploading the profile picture")
            }
        } else {
            println("Updating user info and pfp is empty")
            userRemoteSource.updateUserInfo(user)
        }
    }

    override suspend fun updatePhoneNumber(userId: String, phoneNumber: String): Result<Nothing> {
        return userRemoteSource.updatePhoneNumber(userId, phoneNumber)
    }

    override suspend fun updateName(userId: String, name: String): Result<Nothing> {
        return userRemoteSource.updateName(userId, name)
    }

    override suspend fun getUserSettings(): Result<UserSettings> {
        return userRemoteSource.getUserSettings().let { result ->
            if (result is Result.SuccessWithData)
                Result.SuccessWithData(result.data.copy(theme = localKeyValueStorage.theme ?: "System Default"))
            else
                result
        }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): Result<Nothing> {
        return userRemoteSource.changePassword(oldPassword, newPassword)
    }

    override suspend fun changeEmail(userId: String, email: String): Result<Nothing> {
        return userRemoteSource.changeEmail(userId, email)
    }

    override suspend fun updateStringRemoteUserSetting(
        userId: String,
        tag: String,
        value: String
    ): Result<Nothing> {
        return  userRemoteSource.updateStringRemoteUserSetting(userId, tag, value)
    }

    override suspend fun updateBooleanRemoteUserSetting(
        userId: String,
        tag: String,
        value: Boolean
    ): Result<Nothing> {
        return userRemoteSource.updateBooleanRemoteUserSetting(userId, tag, value)
    }

    override suspend fun updateUserAvatar(avatarByteArray: ByteArray, userId: String): Result<Nothing> {
        return userRemoteSource.updateUserAvatar(avatarByteArray, userId)
    }


    override fun fetchUsers() = userRemoteSource.fetchUsers()
    override fun getUsersByIds(Ids: List<String>) =
        userRemoteSource.getUsersByIds(GetUsersByIdsRequest(Ids))

    override suspend fun createRemoteUser(user: User): Results<DataSuccess.User, DataError.Network> =
        userRemoteSource.createRemoteUser(user)

    override fun getUserCommunities(userId: String): Flow<Results<List<Community>, DataError.Network>> =
        userRemoteSource.getUserCommunities(userId)

    override fun communityLogout(
        id: String,
        selectedCommunityId: String
    ): Flow<Results<List<Community>, DataError.Network>> {
        return userRemoteSource.communityLogout(id, selectedCommunityId)
    }

    override fun joinCommunity(
        id: String,
        code: String
    ): Flow<Results<List<Community>, DataError.Network>> {
        return userRemoteSource.joinCommunity(id, code)
    }
    override suspend fun getTheme(): Result<String> {
        return try{
            Result.SuccessWithData(localKeyValueStorage.theme ?: "System Default")
        } catch (e: Exception){
            Result.Error(e.message ?: "Error getting theme")
        }
    }
    override suspend fun setTheme(theme: String) {
        localKeyValueStorage.theme = theme
    }

}