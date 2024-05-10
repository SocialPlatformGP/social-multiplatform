package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.UserError
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    suspend fun updateUserInfo(user: User): Result<Unit, UserError.UpdateUserInfo>
    suspend fun updatePhoneNumber(userId: String, phoneNumber: String): Result<Unit, UserError.UpdatePhoneNumber>
    suspend fun updateName(userId: String, name: String): Result<Unit, UserError.UpdateName>
    suspend fun getUserSettings(): Result<UserSettings, UserError.GetUserSettings>
    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit, UserError.ChangePassword>
    suspend fun changeEmail(userId: String, email: String): Result<Unit, UserError.ChangeEmail>
    suspend fun updateStringRemoteUserSetting(userId: String, tag: String, value: String): Result<Unit, UserError.UpdateUserSetting>
    suspend fun updateBooleanRemoteUserSetting(userId: String, tag: String, value: Boolean): Result<Unit, UserError.UpdateUserSetting>
    fun fetchUsers(): Flow<Result<List<User>, UserError.FetchUsers>>
    fun getUsersByIds(request: GetUsersByIdsRequest): Flow<Result<List<User>, UserError.FetchUsers>>
    suspend fun uploadUserPfp(pfpByteArray: ByteArray, userId: String): Result<String, UserError.UpdateUserInfo>
    suspend fun updateUserAvatar(avatarByteArray: ByteArray, userId: String): Result<Unit, UserError.UpdateUserAvatar>
    suspend fun createRemoteUser(user: User): Result<Unit, UserError.CreateRemoteUser>
    fun getUserCommunities(userId: String): Flow<Result<List<Community>, UserError.GetUserCommunities>>
    fun communityLogout(
        id: String,
        selectedCommunityId: String
    ): Flow<Result<List<Community>, UserError.CommunityLogout>>

    fun joinCommunity(id: String, code: String): Flow<Result<List<Community>, UserError.JoinCommunity>>
}