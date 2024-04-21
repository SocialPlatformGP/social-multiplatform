package com.gp.socialapp.data.material.repository


import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.data.material.source.local.MaterialLocalDataSource
import com.gp.socialapp.data.material.source.remote.MaterialRemoteDataSource
import com.gp.socialapp.data.material.utils.FileManager
import com.gp.socialapp.util.Platform
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.getPlatform
import kotlinx.coroutines.flow.Flow

class MaterialRepositoryImpl(
    private val remoteDataSource: MaterialRemoteDataSource,
    private val localDataSource: MaterialLocalDataSource,
    private val fileManager: FileManager
) : MaterialRepository {
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

    override suspend fun downloadFile(url: String, mimeType: String) {
        remoteDataSource.downloadFile(url)
    }

    override suspend fun openFile(fileId: String, url: String, mimeType: String) {
        localDataSource.getFilePath(fileId).collect { result ->
            if (result is Result.SuccessWithData) {
                fileManager.openFile(result.data.localPath, mimeType)
            } else {
                println("Error opening file")
                println("DOWNLOADING FILE")
                val data = remoteDataSource.downloadFile(url)
                if (data is Result.SuccessWithData) {
                    val localPath =
                        fileManager.saveFile(data.data.data, data.data.fileName, mimeType)
                    localDataSource.insertFile(MaterialFile(id = fileId, localPath = localPath))
                    fileManager.openFile(localPath, mimeType)
                } else {
                    println("Failed to download file")
                }
            }
        }

    }

    override suspend fun shareLink(url: String) {
        if (getPlatform() == Platform.ANDROID) {
            fileManager.shareLink(url)
        }
    }
}