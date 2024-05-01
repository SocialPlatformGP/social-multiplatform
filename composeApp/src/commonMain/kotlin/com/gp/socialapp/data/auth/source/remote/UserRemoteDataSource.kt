package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.DataSuccess
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    suspend fun updateUserInfo(user: User): Result<Nothing>
    suspend fun updatePhoneNumber(userId: String, phoneNumber: String): Result<Nothing>
    suspend fun updateName(userId: String, name: String): Result<Nothing>
    suspend fun getUserSettings(): Result<UserSettings>
    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Nothing>
    suspend fun changeEmail(userId: String, email: String): Result<Nothing>
    suspend fun updateStringRemoteUserSetting(userId: String, tag: String, value: String): Result<Nothing>
    suspend fun updateBooleanRemoteUserSetting(userId: String, tag: String, value: Boolean): Result<Nothing>
    fun fetchUsers(): Flow<Result<List<User>>>
    fun getUsersByIds(request: GetUsersByIdsRequest): Flow<Results<List<User>, DataError.Network>>
    suspend fun uploadUserPfp(pfpByteArray: ByteArray, userId: String): Result<String>
    suspend fun updateUserAvatar(avatarByteArray: ByteArray, userId: String): Result<Nothing>
    suspend fun createRemoteUser(user: User): Results<DataSuccess.User, DataError.Network>
    fun getUserCommunities(userId: String): Flow<Results<List<Community>, DataError.Network>>
    fun communityLogout(
        id: String,
        selectedCommunityId: String
    ): Flow<Results<List<Community>, DataError.Network>>

    fun joinCommunity(id: String, code: String): Flow<Results<List<Community>, DataError.Network>>
}