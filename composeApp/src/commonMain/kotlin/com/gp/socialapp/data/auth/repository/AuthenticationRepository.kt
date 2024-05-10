package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.AuthError
import com.gp.socialapp.util.Result
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun sendPasswordResetEmail(email: String): Flow<Result<Unit,AuthError.SendPasswordResetEmail>>
    fun clearStorage()
    fun signInWithOAuth(provider: OAuthProvider): Flow<Result<User,AuthError.SignInWithOAuth>>
    fun signInWithEmail(email: String, password: String): Flow<Result<User,AuthError.SignInWithEmail>>
    fun signUpWithEmail(email: String, password: String): Flow<Result<User,AuthError.SignUpWithEmail>>
    suspend fun getSignedInUser(): Result<User,AuthError.GetSignedInUser>
    suspend fun logout(): Result<Unit, AuthError.Logout>
    suspend fun deleteAccount(userId: String): Result<Unit, AuthError.DeleteUser>

}
