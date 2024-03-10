package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.IsEmailAvailableRequest
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
private const val BASE_URL = "http://192.168.1.4:8080/"

class AuthenticationRemoteDataSourceImpl : AuthenticationRemoteDataSource {
    val httpClient = HttpClient{
        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }
        private fun HttpRequestBuilder.endPoint(path: String) {
        url {
            takeFrom(BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)
        }
    }
    override fun isEmailAvailable(email: String): Flow<Result<Boolean>> {
        println("isEmailAvailable: $email")
        return flow {
            emit(Result.Loading)
            try{
                val response = httpClient.post {
                    endPoint("isEmailAvailable")
                    setBody(
                        IsEmailAvailableRequest(
                            email = email
                        )
                    )
                }
                println("isUserExists: ${response.status.description}")
                Napier.d("isUserExists: ${response.status.description}")
                Napier.d("isUserExists: ${response.status.value}")
                Napier.d("isUserExists: ${response.bodyAsText()}")
                if (response.status == HttpStatusCode.OK) {
                    emit(Result.SuccessWithData(response.status.isSuccess()))
                } else {
                    emit(Result.Error(response.bodyAsText()))
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message?:"Null"))
            }
        }
    }

    override fun signInUser(email: String, password: String): Flow<Result<User>> {
        TODO("Not yet implemented")
    }

    override fun signUpUser(email: String, password: String): Flow<Result<User>> {
        TODO("Not yet implemented")
    }

    override fun getSignedInUser(): User? {
        TODO("Not yet implemented")
    }

    override fun sendPasswordResetEmail(email: String): Flow<Result<Nothing>> {
        TODO("Not yet implemented")
    }
}