package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.DataSuccess
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun updateUserInfo(user: User, pfpByteArray: ByteArray): Result<Nothing>

    fun fetchUsers(): Flow<Result<List<User>>>
    fun getUsersByIds(Ids: List<String>): Flow<Result<List<User>>>
    suspend fun createRemoteUser(user: User): Results<DataSuccess.User, DataError.Network>
}
