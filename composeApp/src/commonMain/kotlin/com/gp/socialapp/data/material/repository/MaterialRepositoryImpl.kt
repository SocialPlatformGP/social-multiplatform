package com.gp.socialapp.data.material.repository


import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.data.material.source.remote.MaterialRemoteDataSource
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class MaterialRepositoryImpl(private val remoteDataSource: MaterialRemoteDataSource) :
    MaterialRepository {
    override suspend fun getMaterialAtPath(path: String): Flow<Result<MaterialResponse.GetMaterialResponses>> {
        return remoteDataSource.getMaterialAtPath(path)
    }


    override suspend fun createFolder(
        name: String,
        path: String,
    ): Flow<Result<MaterialResponse.GetMaterialResponses>> {
        return remoteDataSource.createFolder(name, path)
    }

    override suspend fun createFile(
        name: String,
        type: String,
        path: String,
        content: ByteArray
    ): Flow<Result<MaterialResponse.GetMaterialResponses>> {
        return remoteDataSource.createFile(name, type, path, content)
    }

    override suspend fun deleteFile(
        fileId: String,
        path: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses>> {
        return remoteDataSource.deleteFile(fileId, path)
    }

    override suspend fun deleteFolder(folderId: String): Flow<Result<MaterialResponse.GetMaterialResponses>> {
        return remoteDataSource.deleteFolder(folderId)
    }

    override suspend fun downloadFile(url: String) {
        remoteDataSource.downloadFile(url)
    }
}