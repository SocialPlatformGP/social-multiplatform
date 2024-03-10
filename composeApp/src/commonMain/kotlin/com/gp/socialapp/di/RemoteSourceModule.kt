package com.gp.socialapp.di

import com.eygraber.uri.Uri
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSourceImpl
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single<UserRemoteDataSource> {
        object : UserRemoteDataSource {
            val httpClient = HttpClient{

            }
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
    single<AuthenticationRemoteDataSource> {AuthenticationRemoteDataSourceImpl()}
}