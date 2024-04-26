package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.DataSuccess
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun updateUserInfo(user: User, pfpByteArray: ByteArray): Result<Nothing>

    fun fetchUsers(): Flow<Result<List<User>>>
    fun getUsersByIds(Ids: List<String>): Flow<Results<List<User>, DataError.Network>>
    suspend fun createRemoteUser(user: User): Results<DataSuccess.User, DataError.Network>
    fun getUserCommunities(userId: String): Flow<Results<List<Community>, DataError.Network>>
    fun communityLogout(
        id: String,
        selectedCommunityId: String
    ): Flow<Results<List<Community>, DataError.Network>>

    fun joinCommunity(id: String, code: String): Flow<Results<List<Community>, DataError.Network>>
}
