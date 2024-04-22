package com.gp.socialapp.data.auth.source.remote

import com.eygraber.uri.Uri
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUsersByIdsRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.Result
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.storage.storage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.put

class UserRemoteDataSourceImpl(
    private val httpClient: HttpClient,
    private val supabaseClient: SupabaseClient
) : UserRemoteDataSource {
    override suspend fun updateUserInfo(user: User): Result<Nothing> {
        return try {
            supabaseClient.auth.updateUser {
                phone = user.phoneNumber
                data{
                    put(UserData.FIRST_NAME.value, user.firstName)
                    put(UserData.LAST_NAME.value, user.lastName)
                    put(UserData.BIRTH_DATE.value, user.birthdate)
                    put(UserData.PROFILE_PICTURE_URL.value, user.profilePictureURL)
                    put(UserData.BIO.value, user.bio)
                    put(UserData.IS_DATA_COMPLETE.value, true)
                }
            }
            Result.Success
        } catch (e: Exception) {
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }

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

    override fun fetchUsers(): Flow<Result<List<User>>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint("getAllUsers")
            }
            if (response.status == HttpStatusCode.OK) {
                val users = response.body<List<User>>()
                emit(Result.SuccessWithData(users))
            } else {
                emit(Result.Error("An unknown error occurred ${response.status}"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override fun getCurrentUserEmail(): String {
        TODO("Not yet implemented")
    }

    override fun getUsersByIds(request: GetUsersByIdsRequest): Flow<Result<List<User>>> = flow {
        println("Request: $request")
        emit(Result.Loading)
        try {
            val response = httpClient.post {
                endPoint("getUsersByIds")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                val users = response.body<List<User>>()
                println("Users: $users")
                emit(Result.SuccessWithData(users))
            } else {
                emit(Result.Error("An unknown error occurred ${response.status.description}"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun uploadUserPfp(pfpByteArray: ByteArray, userId: String): Result<String> {
        return try {
            val path = "${userId.first()}/$userId"
            val bucket = supabaseClient.storage.from("avatars")
            bucket.upload(path, pfpByteArray, upsert = true)
            val url = supabaseClient.storage.from("avatars").publicUrl(path)
            Result.SuccessWithData(url)
        } catch (e: Exception) {
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }
}