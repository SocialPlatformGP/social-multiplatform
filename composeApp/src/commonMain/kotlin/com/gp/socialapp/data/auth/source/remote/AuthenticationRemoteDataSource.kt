package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.util.Result
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import kotlinx.coroutines.flow.Flow

interface AuthenticationRemoteDataSource {
    suspend fun deleteAccount(userId: String): Result<Nothing>
    fun sendPasswordResetEmail(email: String): Flow<Result<Nothing>>
    fun signInWithOAuth(provider: OAuthProvider): Flow<Result<User>>
    fun signInWithEmail(email: String, password: String): Flow<Result<User>>
    fun signUpWithEmail(email: String, password: String): Flow<Result<User>>
    suspend fun getSignedInUser(): Result<User>
    suspend fun getUserSettings(): Result<UserSettings>
    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Nothing>
    suspend fun changeEmail(email: String): Result<Nothing>
    suspend fun updateStringRemoteUserSetting(tag: String, value: String): Result<Nothing>
    suspend fun updateBooleanRemoteUserSetting(tag: String, value: Boolean): Result<Nothing>
    suspend fun logout(): Result<Nothing>
}