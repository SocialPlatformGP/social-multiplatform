package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    suspend fun updateUserInfo(user: User): Result<Nothing>

    fun fetchUsers(): Flow<Result<List<User>>>
    fun getUsersByIds(request: GetUsersByIdsRequest): Flow<Result<List<User>>>
    suspend fun uploadUserPfp(pfpByteArray: ByteArray, userId: String): Result<String>
    suspend fun createRemoteUser(user: User): Result<Nothing>
}