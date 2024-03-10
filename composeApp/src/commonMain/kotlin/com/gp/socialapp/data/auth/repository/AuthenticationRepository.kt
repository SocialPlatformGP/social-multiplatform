package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun isEmailAvailable(email: String): Flow<Result<Boolean>>

    fun signInUser(email: String, password: String): Flow<Result<User>>
    fun signUpUser(email: String, password: String): Flow<Result<User>>
    fun getSignedInUser(): User?

    //    fun authenticateWithGoogle(account: GoogleSignInAccount): Flow<State<FirebaseUser>>
    fun sendPasswordResetEmail(email: String): Flow<Result<Nothing>>
}