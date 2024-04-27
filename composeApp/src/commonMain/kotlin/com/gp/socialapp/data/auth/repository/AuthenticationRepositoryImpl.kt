package com.gp.socialapp.data.auth.repository

import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorage
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.Result
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import kotlinx.coroutines.flow.Flow

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localKeyValueStorage: AuthKeyValueStorage
) : AuthenticationRepository {

    override fun sendPasswordResetEmail(email: String) =
        remoteDataSource.sendPasswordResetEmail(email)

    override fun clearStorage() {
        localKeyValueStorage.cleanStorage()
    }

    override fun signInWithOAuth(provider: OAuthProvider): Flow<Result<User>> =
        remoteDataSource.signInWithOAuth(provider)

    override fun signInWithEmail(email: String, password: String): Flow<Result<User>> =
        remoteDataSource.signInWithEmail(email, password)

    override fun signUpWithEmail(email: String, password: String): Flow<Result<User>> =
        remoteDataSource.signUpWithEmail(email, password)

    override suspend fun getSignedInUser(): Result<User> =
        remoteDataSource.getSignedInUser()

    override suspend fun logout(): Result<Nothing> {
        clearStorage()
        return remoteDataSource.logout()
    }


}
