package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull


class AuthenticationRemoteDataSourceImpl(
    private val supabaseClient: SupabaseClient
) : AuthenticationRemoteDataSource {

    private val sessionStatusFlow = supabaseClient.auth.sessionStatus
    override fun signInWithOAuth(provider: OAuthProvider): Flow<Result<User>> = flow {
        emit(Result.Loading)
        try {
            supabaseClient.auth.signInWith(provider) {
                scopes.addAll(listOf("email", "profile"))
            }
            sessionStatusFlow.collect { session ->
                when (session) {
                    is SessionStatus.Authenticated -> {
                        getSignedInUser().collect { result ->
                            emit(result)
                        }
                    }

                    SessionStatus.LoadingFromStorage -> {
                        Napier.e("Loading from storage")
                        emit(Result.Loading)
                    }

                    SessionStatus.NetworkError -> {
                        Napier.e("Network Error")
                        emit(Result.Error("Network Error"))
                    }

                    is SessionStatus.NotAuthenticated -> {

                        emit(Result.Error("Not Authenticated"))
                    }
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Null"))
        }
    }

    override fun signInWithEmail(
        email: String,
        password: String
    ): Flow<Result<User>> = flow {
        emit(Result.Loading)
        try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            sessionStatusFlow.collect {
                when (it) {
                    is SessionStatus.Authenticated -> {
                        getSignedInUser().collect {
                            emit(it)
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
    ): Flow<Result<User>> = flow {
        emit(Result.Loading)
        try {
            supabaseClient.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            sessionStatusFlow.collect {
                when (it) {
                    is SessionStatus.Authenticated -> {
                        getSignedInUser().collect {
                            emit(it)
                        }
                    }

                    else -> Unit
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Null"))
        }
    }

    override fun getSignedInUser(): Flow<Result<User>> = flow {
        val userInfo = supabaseClient.auth.sessionManager.loadSession()?.user
        if (userInfo != null) {
            println("User Info: ${userInfo.userMetadata}")
            val isUserDataComplete =
                userInfo.userMetadata?.get("isUserDataComplete")?.jsonPrimitive?.booleanOrNull
                    ?: false
            val user = if (isUserDataComplete) {
                val id = userInfo.id
                val firstName =
                    userInfo.userMetadata?.get(UserData.FIRST_NAME.value)?.jsonPrimitive?.contentOrNull
                        ?: ""
                val lastName =
                    userInfo.userMetadata?.get(UserData.LAST_NAME.value)?.jsonPrimitive?.contentOrNull
                        ?: ""
                val email = userInfo.email ?: ""
                val pfpUrl =
                    userInfo.userMetadata?.get(UserData.PROFILE_PICTURE_URL.value)?.jsonPrimitive?.contentOrNull
                        ?: ""
                val phoneNumber = userInfo.phone ?: ""
                val birthDate =
                    userInfo.userMetadata?.get(UserData.BIRTH_DATE.value)?.jsonPrimitive?.longOrNull
                        ?: 0L
                val bio =
                    userInfo.userMetadata?.get(UserData.BIO.value)?.jsonPrimitive?.contentOrNull
                        ?: ""
                val createdAt = userInfo.createdAt?.epochSeconds ?: 0L
                val isAdmin =
                    userInfo.userMetadata?.get(UserData.IS_ADMIN.value)?.jsonPrimitive?.booleanOrNull
                        ?: false
                User(
                    id = id,
                    firstName = firstName,
                    lastName = lastName,
                    profilePictureURL = pfpUrl,
                    email = email,
                    phoneNumber = phoneNumber,
                    birthdate = birthDate,
                    bio = bio,
                    createdAt = createdAt,
                    isAdmin = isAdmin,
                    isDataComplete = isUserDataComplete
                )
            } else {
                val id = userInfo.id
                val email = userInfo.email ?: ""
                User(
                    id = id,
                    email = email,
                    isDataComplete = false
                )
            }
            emit(Result.SuccessWithData(user))
        } else {
            emit(Result.Error("User not found"))
        }
    }

    override suspend fun logout(): Result<Nothing> {
        return try {
            supabaseClient.auth.signOut()
            Result.Success
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Null")
        }
    }


    override fun sendPasswordResetEmail(email: String): Flow<Result<Nothing>> {
        TODO("Not yet implemented")
    }
}

enum class UserData(val value: String) {
    FIRST_NAME("first_name"),
    LAST_NAME("last_name"),
    PROFILE_PICTURE_URL("profile_picture_url"),
    BIRTH_DATE("birth_date"),
    BIO("bio"),
    IS_ADMIN("isAdmin"),
    IS_DATA_COMPLETE("isUserDataComplete")
}