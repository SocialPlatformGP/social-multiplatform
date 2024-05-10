package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorage
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.UserError
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userRemoteSource: UserRemoteDataSource,
    private val localKeyValueStorage: AuthKeyValueStorage
) : UserRepository {
    override suspend fun updateUserInfo(user: User, pfpByteArray: ByteArray): Result<Unit,UserError.UpdateUserInfo> {
        return if (pfpByteArray.isNotEmpty()) {
            val result = userRemoteSource.uploadUserPfp(pfpByteArray, user.id)
            if (result is Result.Success) {
                userRemoteSource.updateUserInfo(user.copy(profilePictureURL = result.data))
            } else {
                error(UserError.UpdateUserInfo.SERVER_ERROR)
            }
        } else {
            userRemoteSource.updateUserInfo(user)
        }
    }

    override suspend fun updatePhoneNumber(userId: String, phoneNumber: String): Result<Unit, UserError.UpdatePhoneNumber> {
        return userRemoteSource.updatePhoneNumber(userId, phoneNumber)
    }

    override suspend fun updateName(userId: String, name: String): Result<Unit, UserError.UpdateName> {
        return userRemoteSource.updateName(userId, name)
    }

    override suspend fun getUserSettings(): Result<UserSettings, UserError.GetUserSettings> {
        return userRemoteSource.getUserSettings().let { result ->
            if (result is Result.Success)
                Result.Success(result.data.copy(theme = localKeyValueStorage.theme ?: "System Default"))
            else
                result
        }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit, UserError.ChangePassword> {
        return userRemoteSource.changePassword(oldPassword, newPassword)
    }

    override suspend fun changeEmail(userId: String, email: String): Result<Unit, UserError.ChangeEmail> {
        return userRemoteSource.changeEmail(userId, email)
    }

    override suspend fun updateStringRemoteUserSetting(
        userId: String,
        tag: String,
        value: String
    ): Result<Unit, UserError.UpdateUserSetting> {
        return  userRemoteSource.updateStringRemoteUserSetting(userId, tag, value)
    }

    override suspend fun updateBooleanRemoteUserSetting(
        userId: String,
        tag: String,
        value: Boolean
    ): Result<Unit, UserError.UpdateUserSetting> {
        return userRemoteSource.updateBooleanRemoteUserSetting(userId, tag, value)
    }

    override suspend fun updateUserAvatar(avatarByteArray: ByteArray, userId: String): Result<Unit, UserError.UpdateUserAvatar> {
        return userRemoteSource.updateUserAvatar(avatarByteArray, userId)
    }


    override fun fetchUsers() = userRemoteSource.fetchUsers()
    override fun getUsersByIds(Ids: List<String>) =
        userRemoteSource.getUsersByIds(GetUsersByIdsRequest(Ids))

    override suspend fun createRemoteUser(user: User): Result<Unit, UserError.CreateRemoteUser> =
        userRemoteSource.createRemoteUser(user)

    override fun getUserCommunities(userId: String): Flow<Result<List<Community>, UserError.GetUserCommunities>> =
        userRemoteSource.getUserCommunities(userId)

    override fun communityLogout(
        id: String,
        selectedCommunityId: String
    ): Flow<Result<List<Community>, UserError.CommunityLogout>> {
        return userRemoteSource.communityLogout(id, selectedCommunityId)
    }

    override fun joinCommunity(
        id: String,
        code: String
    ): Flow<Result<List<Community>, UserError.JoinCommunity>> {
        return userRemoteSource.joinCommunity(id, code)
    }
    override suspend fun getTheme(): Result<String, UserError.GetTheme> {
        return try{
            Result.Success(localKeyValueStorage.theme ?: "System Default")
        } catch (e: Exception){
            error(UserError.GetTheme.SERVER_ERROR)
        }
    }
    override suspend fun setTheme(theme: String) {
        localKeyValueStorage.theme = theme
    }

}