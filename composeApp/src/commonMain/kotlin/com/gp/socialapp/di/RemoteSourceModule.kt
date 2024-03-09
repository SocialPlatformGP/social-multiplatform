package com.gp.socialapp.di

import com.eygraber.uri.Uri
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single<UserRemoteDataSource> {
        object : UserRemoteDataSource {
            override fun createUser(user: User, pfpURI: Uri): Flow<Result<Nothing>> {
                TODO("Not yet implemented")
            }

            override fun updateUser(user: User): Flow<Result<Nothing>> {
                TODO("Not yet implemented")
            }

            override fun deleteUser(user: User): Flow<Result<Nothing>> {
                TODO("Not yet implemented")
            }

            override suspend fun fetchUser(email: String): Result<User> {
                TODO("Not yet implemented")
            }

            override fun fetchUsers(): Flow<Result<List<User>>> {
                TODO("Not yet implemented")
            }

            override fun getCurrentUserEmail(): String {
                TODO("Not yet implemented")
            }

            override fun getUsersByEmails(emails: List<String>): Flow<Result<List<User>>> {
                TODO("Not yet implemented")
            }
        }
    }
    single<AuthenticationRemoteDataSource> {
        object : AuthenticationRemoteDataSource {
            override fun signInUser(email: String, password: String): Flow<Result<User>> {
                TODO("Not yet implemented")
            }

            override fun signUpUser(email: String, password: String): Flow<Result<User>> {
                TODO("Not yet implemented")
            }

            override fun getSignedInUser(): User? {
                TODO("Not yet implemented")
            }

            override fun sendPasswordResetEmail(email: String): Flow<Result<Nothing>> {
                TODO("Not yet implemented")
            }
        }
    }
}