package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSource

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource
): AuthenticationRepository {
    override fun signInUser(email: String, password: String) = remoteDataSource.signInUser(email, password)

    override fun signUpUser(email: String, password: String) = remoteDataSource.signUpUser(email, password)

    override fun getSignedInUser() = remoteDataSource.getSignedInUser()

    override fun sendPasswordResetEmail(email: String) = remoteDataSource.sendPasswordResetEmail(email)
}