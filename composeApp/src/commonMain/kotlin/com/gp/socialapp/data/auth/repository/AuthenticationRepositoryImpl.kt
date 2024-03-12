package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorage
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow
import socialmultiplatform.composeapp.generated.resources.Res.string.email
import socialmultiplatform.composeapp.generated.resources.Res.string.password

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localKeyValueStorage: AuthKeyValueStorage
): AuthenticationRepository {
    override fun isEmailAvailable(email: String): Flow<Result<Boolean>> =
        remoteDataSource.isEmailAvailable(email)

    override fun signInUser(email: String, password: String) = remoteDataSource.signInUser(email, password)

    override fun signUpUser(user: User) = remoteDataSource.signUpUser(user.toUserRequest())

    override fun getSignedInUser() = remoteDataSource.getSignedInUser()

    override fun sendPasswordResetEmail(email: String) = remoteDataSource.sendPasswordResetEmail(email)
    override fun getLocalUserToken(): String? {
        return localKeyValueStorage.token
    }

    override fun setLocalUserToken(token: String) {
        localKeyValueStorage.token = token
    }
}