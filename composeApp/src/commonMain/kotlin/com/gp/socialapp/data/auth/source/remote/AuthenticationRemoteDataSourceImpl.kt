package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserRequest
import com.gp.socialapp.data.auth.source.remote.model.requests.GetUserRequest
import com.gp.socialapp.data.auth.source.remote.model.requests.IsEmailAvailableRequest
import com.gp.socialapp.data.auth.source.remote.model.requests.SignInRequest
import com.gp.socialapp.data.auth.source.remote.model.responses.AuthResponse
import com.gp.socialapp.data.auth.source.remote.model.responses.IsEmailAvailableResponse
import com.gp.socialapp.data.auth.source.remote.model.responses.UserResponse
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionSource
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Azure
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow


class AuthenticationRemoteDataSourceImpl(
    private val httpClient: HttpClient,
    private val supabaseClient: SupabaseClient
) : AuthenticationRemoteDataSource {
    override val sessionStatusFlow: StateFlow<SessionStatus>
        get() = supabaseClient.auth.sessionStatus

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

    override fun signInUser(email: String, password: String): Flow<Result<AuthResponse>> {
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
                    when (it.status) {
                        HttpStatusCode.OK -> {
                            emit(Result.SuccessWithData(response))
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

    override fun createRemoteUser(userRequest: UserRequest): Flow<Result<AuthResponse>> {
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
                    when (it.status) {
                        HttpStatusCode.OK -> {
                            emit(Result.SuccessWithData(response))
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

    override fun getSignedInUser(id: String): Flow<Result<User>> {
        println("getSignedInUserRequest:$id")
        val request = GetUserRequest(id)
        return flow {
            emit(Result.Loading)
            try {
                httpClient.get {
                    endPoint("getSignedUser")
                    setBody(
                        request
                    )
                }.let {
                    val response = it.body<UserResponse>().toUser()
                    println("getSignedInUserResponse:$response")
                    Napier.d("getSignedInUser : ${it.status} ${response}")
                    when (it.status) {
                        HttpStatusCode.OK -> {
                            emit(Result.SuccessWithData(response))
                        }

                        else -> {
                            emit(Result.Error(it.status.description + " " + "Error"))
                        }
                    }
                }
            } catch (e: Exception) {
                Napier.e("getSignedInUser: ${e.message}")
                emit(Result.Error(e.message ?: "Null"))
            }
        }
    }

    override fun signInWithOAuth(provider: OAuthProvider): Flow<Result<Pair<UserInfo, SessionSource>>> = flow {
        emit(Result.Loading)
        try {
            supabaseClient.auth.signInWith(provider) {
                scopes.addAll(listOf("email", "profile"))
            }
            sessionStatusFlow.collect {
                when (it) {
                    is SessionStatus.Authenticated -> {
                        val user = supabaseClient.auth.sessionManager.loadSession()?.user
                        if (user != null) {
                            emit(Result.SuccessWithData(Pair(user, it.source)))
                        } else {
                            emit(Result.Error("User is null"))
                        }
                    }

                    else -> Unit
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Null"))
        }
    }

    override fun signInWithEmail(
        email: String,
        password: String
    ): Flow<Result<Pair<UserInfo, SessionSource>>> = flow {
        emit(Result.Loading)
        try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            sessionStatusFlow.collect {
                when (it) {
                    is SessionStatus.Authenticated -> {
                        val user = supabaseClient.auth.sessionManager.loadSession()?.user
                        if (user != null) {
                            emit(Result.SuccessWithData(Pair(user, it.source)))
                        } else {
                            emit(Result.Error("User is null"))
                        }
                    }

                    else -> Unit
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Null"))
        }
    }

    override fun signUpWithEmail(
        email: String,
        password: String
    ): Flow<Result<Pair<UserInfo, SessionSource>>> = flow {
        emit(Result.Loading)
        try {
            supabaseClient.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            sessionStatusFlow.collect {
                when (it) {
                    is SessionStatus.Authenticated -> {
                        val user = supabaseClient.auth.sessionManager.loadSession()?.user
                        if (user != null) {
                            emit(Result.SuccessWithData(Pair(user, it.source)))
                        } else {
                            emit(Result.Error("User is null"))
                        }
                    }

                    else -> Unit
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Null"))
        }
    }


    override fun sendPasswordResetEmail(email: String): Flow<Result<Nothing>> {
        TODO("Not yet implemented")
    }
}