package com.gp.socialapp.data.auth.source.remote

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.auth.source.remote.model.UserRequest
import com.gp.socialapp.data.auth.source.remote.model.responses.AuthResponse
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthenticationRemoteDataSource {
    fun isEmailAvailable(email: String): Flow<Result<Boolean>>
    fun signInUser(email: String, password: String): Flow<Result<AuthResponse>>
    fun signUpUser(userRequest: UserRequest): Flow<Result<AuthResponse>>
    fun getSignedInUser(id: String): Flow<Result<User>>
    fun sendPasswordResetEmail(email: String): Flow<Result<Nothing>>
    suspend fun signInWithMicrosoft()
}