package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserSettings
import com.gp.socialapp.util.AuthError
import com.gp.socialapp.util.Result
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import kotlinx.coroutines.flow.Flow

interface AuthenticationRemoteDataSource {
    suspend fun deleteAccount(userId: String): Result<Unit,AuthError.DeleteUser>
    fun sendPasswordResetEmail(email: String): Flow<Result<Unit,AuthError.SendPasswordResetEmail>>
    fun signInWithOAuth(provider: OAuthProvider): Flow<Result<User,AuthError.SignInWithOAuth>>
    fun signInWithEmail(email: String, password: String): Flow<Result<User,AuthError.SignInWithEmail>>
    fun signUpWithEmail(email: String, password: String): Flow<Result<User,AuthError.SignUpWithEmail>>
    suspend fun getSignedInUser(): Result<User,AuthError.GetSignedInUser>
    suspend fun getUserSettings(): Result<UserSettings,AuthError.GetUserSettings>
    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit,AuthError.ChangePassword>
    suspend fun changeEmail(email: String): Result<Unit,AuthError.ChangeEmail>
    suspend fun updateStringRemoteUserSetting(tag: String, value: String): Result<Unit,AuthError.UpdateUserSetting>
    suspend fun updateBooleanRemoteUserSetting(tag: String, value: Boolean): Result<Unit,AuthError.UpdateUserSetting>
    suspend fun logout(): Result<Unit,AuthError.Logout>
}