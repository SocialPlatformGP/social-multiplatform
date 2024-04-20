package com.gp.socialapp.data.auth.source.remote

import com.eygraber.uri.Uri
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    fun createUser(user: User, pfpURI: Uri): Flow<Result<Nothing>>
    fun updateUser(user: User): Flow<Result<Nothing>>
    fun deleteUser(user: User): Flow<Result<Nothing>>
    suspend fun fetchUser(email: String): Result<User>
    fun fetchUsers(): Flow<Result<List<User>>>
    fun getCurrentUserEmail(): String
    fun getUsersByIds(request: GetUsersByIdsRequest): Flow<Result<List<User>>>
}