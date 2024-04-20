package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorage
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localKeyValueStorage: AuthKeyValueStorage
) : AuthenticationRepository {
    override fun isEmailAvailable(email: String): Flow<Result<Boolean>> =
        remoteDataSource.isEmailAvailable(email)

    override fun signInUser(email: String, password: String) =
        remoteDataSource.signInUser(email, password)

    override fun signUpUser(user: User) =
        remoteDataSource.signUpUser(user.toUserRequest())

    override fun getSignedInUser(id: String) = remoteDataSource.getSignedInUser(id)

    override fun sendPasswordResetEmail(email: String) =
        remoteDataSource.sendPasswordResetEmail(email)

    override fun getLocalUserToken(): String? {
        return localKeyValueStorage.token
    }

    override fun getCurrentLocalUserId(): String {
        return localKeyValueStorage.userId ?: ""
    }

    override fun setLocalUserId(id: String) {
        localKeyValueStorage.userId = id
    }

    override fun setLocalUserToken(token: String) {
        localKeyValueStorage.token = token
    }

    override fun clearStorage() {
        localKeyValueStorage.cleanStorage()
    }

    override suspend fun signInWithMicrosoft() {
        remoteDataSource.signInWithMicrosoft()
    }
}
