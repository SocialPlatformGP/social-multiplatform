package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserRequest
import com.gp.socialapp.data.auth.source.remote.model.requests.IsEmailAvailableRequest
import com.gp.socialapp.data.auth.source.remote.model.requests.SignInRequest
import com.gp.socialapp.data.auth.source.remote.model.responses.AuthResponse
import com.gp.socialapp.data.auth.source.remote.model.responses.IsEmailAvailableResponse
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import socialmultiplatform.composeapp.generated.resources.Res.string.email
import socialmultiplatform.composeapp.generated.resources.Res.string.password

private const val BASE_URL = "http://192.168.1.4:8080/"

class AuthenticationRemoteDataSourceImpl : AuthenticationRemoteDataSource {
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
                isLenient = true
                encodeDefaults = true
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
        val request = IsEmailAvailableRequest(email)
        println("isEmailAvailableRequest:$request")
        return flow {
            emit(Result.Loading)
            try {
                httpClient.post {
                    endPoint("isEmailAvailable")
                    setBody(
                        request
                    )
                }.let {
                    val response = it.body<IsEmailAvailableResponse>()
                    println("isEmailAvailableResponse:$response")
                    Napier.d("isEmailAvailableResponse : ${it.status} ${response.message} ${response.isAvailable}")
                    when (it.status) {
                        HttpStatusCode.OK -> {
                            emit(Result.SuccessWithData(response.isAvailable))
                        }
                        else -> {
                            emit(Result.Error(it.status.description + " " + response.message))
                        }
                    }
                }

            } catch (e: Exception) {
                Napier.e("isEmailAvailable: ${e.message}")
                emit(Result.Error(e.message ?: "Null"))
            }
        }
    }

    override fun signInUser(email: String, password: String): Flow<Result<String>> {
        val request = SignInRequest(email, password)
        println("signinRequest:$request")
        return flow {
            emit(Result.Loading)
            try {
                httpClient.post {
                    endPoint("signin")
                    setBody(
                        request
                    )
                }.let {
                    val response = it.body<AuthResponse>()
                    println("signinResponse:$response")
                    Napier.d("signin : ${it.status} ${response.errorMessage} ${response.token}")
                    when (it.status) {
                        HttpStatusCode.OK -> {
                            emit(Result.SuccessWithData(response.token))
                        }

                        else -> {
                            emit(Result.Error(it.status.description + " " + response.errorMessage))
                        }
                    }
                }
            } catch (e: Exception) {
                Napier.e("signin: ${e.message}")
                emit(Result.Error(e.message ?: "Null"))
            }
        }
    }

    override fun signUpUser(userRequest: UserRequest): Flow<Result<String>> {
        println("signupRequest:$userRequest")
        return flow {
            emit(Result.Loading)
            try {
                httpClient.post {
                    endPoint("signup")
                    setBody(
                        userRequest
                    )
                }.let {
                    val response = it.body<AuthResponse>()
                    println("signupResponse:$response")
                    Napier.d("signup : ${it.status} ${response.errorMessage} ${response.token}")
                    when (it.status) {
                        HttpStatusCode.OK -> {
                            emit(Result.SuccessWithData(response.token))
                        }

                        else -> {
                            emit(Result.Error(it.status.description + " " + response.errorMessage))
                        }
                    }
                }
            } catch (e: Exception) {
                Napier.e("signup: ${e.message}")
                emit(Result.Error(e.message ?: "Null"))
            }
        }
    }

    override fun getSignedInUser(): User? {
        TODO("Not yet implemented")
    }

    override fun sendPasswordResetEmail(email: String): Flow<Result<Nothing>> {
        TODO("Not yet implemented")
    }
}