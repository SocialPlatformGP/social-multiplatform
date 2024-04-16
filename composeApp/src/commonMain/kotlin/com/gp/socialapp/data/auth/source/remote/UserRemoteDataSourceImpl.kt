package com.gp.socialapp.data.auth.source.remote

import com.eygraber.uri.Uri
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRemoteDataSourceImpl(
    private val httpClient: HttpClient
): UserRemoteDataSource {
    override fun createUser(user: User, pfpURI: Uri): Flow<Result<Nothing>> {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: User): Flow<Result<Nothing>> {
        TODO("Not yet implemented")
    }

    override fun deleteUser(user: User): Flow<Result<Nothing>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUser(email: String): Result<User> {
        TODO("Not yet implemented")
    }

    override fun fetchUsers(): Flow<Result<List<User>>>  = flow {
        emit(Result.Loading)
        try{
            val response = httpClient.get {
                endPoint("getAllUsers")
            }
            if(response.status == HttpStatusCode.OK) {
                val users = response.body<List<User>>()
                emit(Result.SuccessWithData(users))
            } else {
                emit(Result.Error("An unknown error occurred ${response.status}"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message?: "An unknown error occurred"))
        }
    }

    override fun getCurrentUserEmail(): String {
        TODO("Not yet implemented")
    }

    override fun getUsersByIds(request: GetUsersByIdsRequest): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.post {
                endPoint("getUsersByIds")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                val users = response.body<List<User>>()
                emit(Result.SuccessWithData(users))
            } else {
                emit(Result.Error("An unknown error occurred ${response.status.description}"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message?: "An unknown error occurred"))
        }
    }
}