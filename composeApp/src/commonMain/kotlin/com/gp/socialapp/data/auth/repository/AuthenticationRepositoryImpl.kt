package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSource
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource
): AuthenticationRepository {
    override fun isEmailAvailable(email: String): Flow<Result<Boolean>> =
        remoteDataSource.isEmailAvailable(email)

    override fun signInUser(email: String, password: String) = remoteDataSource.signInUser(email, password)

    override fun signUpUser(email: String, password: String) = remoteDataSource.signUpUser(email, password)

    override fun getSignedInUser() = remoteDataSource.getSignedInUser()

    override fun sendPasswordResetEmail(email: String) = remoteDataSource.sendPasswordResetEmail(email)
}