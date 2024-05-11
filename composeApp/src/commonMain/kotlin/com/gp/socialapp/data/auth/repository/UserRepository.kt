package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.DataSuccess
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.UserError
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun updateUserInfo(user: User, pfpByteArray: ByteArray): Result<Unit,UserError>
    suspend fun updatePhoneNumber(userId: String, phoneNumber: String): Result<Unit,UserError>
    suspend fun updateName(userId: String, name: String): Result<Unit,UserError>
    suspend fun getUserSettings(): Result<UserSettings, UserError>
    suspend fun updateUserAvatar(avatarByteArray: ByteArray, userId: String): Result<Unit, UserError>
    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit, UserError>
    suspend fun changeEmail(userId: String, email: String): Result<Unit, UserError>
    suspend fun updateStringRemoteUserSetting(userId: String, tag: String, value: String): Result<Unit, UserError>
    suspend fun updateBooleanRemoteUserSetting(userId: String, tag: String, value: Boolean): Result<Unit, UserError>
    fun fetchUsers(): Flow<Result<List<User>, UserError>>
    fun getUsersByIds(Ids: List<String>): Flow<Result<List<User>, UserError>>
    suspend fun createRemoteUser(user: User): Result<Unit, UserError>
    fun getUserCommunities(userId: String): Flow<Result<List<Community>, UserError>>
    suspend fun getTheme(): Result<String, UserError>
    suspend fun setTheme(theme: String)
    fun communityLogout(
        id: String,
        selectedCommunityId: String
    ): Flow<Result<List<Community>, UserError>>

    fun joinCommunity(id: String, code: String): Flow<Result<List<Community>, UserError>>
}
