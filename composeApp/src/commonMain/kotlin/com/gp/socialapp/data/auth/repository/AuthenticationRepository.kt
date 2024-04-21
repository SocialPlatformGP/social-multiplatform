package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.responses.AuthResponse
import com.gp.socialapp.util.Result
import io.github.jan.supabase.gotrue.SessionSource
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun isEmailAvailable(email: String): Flow<Result<Boolean>>

    fun signInUser(email: String, password: String): Flow<Result<AuthResponse>>
    fun signUpUser(user: User): Flow<Result<AuthResponse>>
    fun getSignedInUser(id: String): Flow<Result<User>>

    //    fun authenticateWithGoogle(account: GoogleSignInAccount): Flow<State<FirebaseUser>>
    fun sendPasswordResetEmail(email: String): Flow<Result<Nothing>>
    fun getLocalUserToken(): String?
    fun getCurrentLocalUserId(): String
    fun setLocalUserId(id: String)
    fun setLocalUserToken(token: String)
    fun clearStorage()
    fun signInWithMicrosoft(provider: OAuthProvider): Flow<Result<Pair<UserInfo, SessionSource>>>
}
