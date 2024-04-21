package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserRequest
import com.gp.socialapp.data.auth.source.remote.model.responses.AuthResponse
import com.gp.socialapp.util.Result
import io.github.jan.supabase.gotrue.SessionSource
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthenticationRemoteDataSource {
    val sessionStatusFlow: StateFlow<SessionStatus>
    fun isEmailAvailable(email: String): Flow<Result<Boolean>>
    fun signInUser(email: String, password: String): Flow<Result<AuthResponse>>
    fun signUpUser(userRequest: UserRequest): Flow<Result<AuthResponse>>
    fun getSignedInUser(id: String): Flow<Result<User>>
    fun sendPasswordResetEmail(email: String): Flow<Result<Nothing>>
    fun signInWithOAuth(provider: OAuthProvider): Flow<Result<Pair<UserInfo, SessionSource>>>
    fun signInWithEmail(email: String, password: String): Flow<Result<Pair<UserInfo, SessionSource>>>
}