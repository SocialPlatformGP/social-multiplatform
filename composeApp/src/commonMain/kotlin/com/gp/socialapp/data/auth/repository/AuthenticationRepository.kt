package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.responses.AuthResponse
import com.gp.socialapp.util.Result
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun sendPasswordResetEmail(email: String): Flow<Result<Nothing>>
    fun getLocalUserToken(): String?
    fun getCurrentLocalUserId(): String
    fun setLocalUserId(id: String)
    fun setLocalUserToken(token: String)
    fun clearStorage()
    fun signInWithOAuth(provider: OAuthProvider): Flow<Result<User>>
    fun signInWithEmail(email: String, password: String): Flow<Result<User>>
    fun signUpWithEmail(email: String, password: String): Flow<Result<User>>
    suspend fun getSignedInUser(): Result<User>
}
