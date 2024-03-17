package com.gp.socialapp.di

import com.eygraber.uri.Uri
import com.gp.material.model.FileType
import com.gp.material.model.MaterialItem
import com.gp.material.source.remote.MaterialRemoteDataSource
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
    single<MaterialRemoteDataSource> {
        object : MaterialRemoteDataSource {
            override fun uploadFile(fileLocation: String, file: Uri): Flow<Result<Nothing>> {
                TODO("Not yet implemented")
            }

            override fun uploadFolder(fileLocation: String, name: String): Flow<Result<Nothing>> {
                TODO("Not yet implemented")
            }

            override fun deleteFile(fileLocation: String): Flow<Result<Nothing>> {
                TODO("Not yet implemented")
            }

            override fun deleteFolder(folderPath: String): Flow<Result<Nothing>> {
                TODO("Not yet implemented")
            }

            override fun getFileTypeFromName(fileName: String): FileType {
                TODO("Not yet implemented")
            }

            override fun uploadMaterialItemToDatabase(materialItem: MaterialItem) {
                TODO("Not yet implemented")
            }

            override fun getListOfFiles(path: String): Flow<Result<List<MaterialItem>>> {
                TODO("Not yet implemented")
            }

        }}
    single<AuthenticationRemoteDataSource> {AuthenticationRemoteDataSourceImpl()}

}