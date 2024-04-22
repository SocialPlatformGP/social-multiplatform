package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.Result
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


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
            sessionStatusFlow.collect {
                when (it) {
                    is SessionStatus.Authenticated -> {
                        val user = getSignedInUser()
                        emit(user)
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
                        val user = getSignedInUser()
                        emit(user)
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
                        val user = getSignedInUser()
                        emit(user)
                    }
                    else -> Unit
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Null"))
        }
    }

    override suspend fun getSignedInUser(): Result<User> {
        val userInfo = supabaseClient.auth.sessionManager.loadSession()?.user
        if (userInfo != null) {
            val isUserDataComplete =
                userInfo.appMetadata?.getOrElse(UserData.IS_DATA_COMPLETE.value) { false } as Boolean
            val user = if (isUserDataComplete) {
                val id = userInfo.id
                val firstName =
                    userInfo.userMetadata?.getOrElse(UserData.FIRST_NAME.value) { "" } as String
                val lastName =
                    userInfo.userMetadata?.getOrElse(UserData.LAST_NAME.value) { "" } as String
                val email = userInfo.email ?: ""
                val pfpUrl =
                    userInfo.userMetadata?.getOrElse(UserData.PROFILE_PICTURE_URL.value) { "" } as String
                val phoneNumber = userInfo.phone ?: ""
                val birthDate =
                    userInfo.userMetadata?.getOrElse(UserData.BIRTH_DATE.value) { 0L } as Long
                val bio = userInfo.userMetadata?.getOrElse(UserData.BIO.value) { "" } as String
                val createdAt = userInfo.createdAt?.epochSeconds ?: 0L
                val isAdmin =
                    userInfo.appMetadata?.getOrElse(UserData.IS_ADMIN.value) { false } as Boolean
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
            return Result.SuccessWithData(user)
        } else {
            return Result.Error("User is null")
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