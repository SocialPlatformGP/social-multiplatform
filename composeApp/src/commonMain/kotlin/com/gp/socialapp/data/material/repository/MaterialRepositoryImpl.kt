package com.gp.socialapp.data.material.repository


import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.data.material.source.local.MaterialLocalDataSource
import com.gp.socialapp.data.material.source.remote.MaterialRemoteDataSource
import com.gp.socialapp.data.material.utils.FileManager
import com.gp.socialapp.util.MaterialError
import com.gp.socialapp.util.Platform
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.getPlatform
import kotlinx.coroutines.flow.Flow

class MaterialRepositoryImpl(
    private val remoteDataSource: MaterialRemoteDataSource,
    private val localDataSource: MaterialLocalDataSource,
    private val fileManager: FileManager
) : MaterialRepository {
    override suspend fun getMaterialAtPath(path: String): Flow<Result<MaterialResponse.GetMaterialResponses,MaterialError>> {
        return remoteDataSource.getMaterialAtPath(path)
    }

    override suspend fun createFolder(
        name: String,
        path: String,
        communityId: String,
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>> {
        return remoteDataSource.createFolder(name, path, communityId)
    }
    override suspend fun createFile(
        name: String,
        type: String,
        path: String,
        content: ByteArray,
        communityId: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>> {
        return remoteDataSource.createFile(name, type, path, content, communityId)
    }

    override suspend fun deleteFile(
        fileId: String,
        path: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>> {
        return remoteDataSource.deleteFile(fileId, path)
    }

    override suspend fun deleteFolder(folderId: String): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>> {
        return remoteDataSource.deleteFolder(folderId)
    }

    override suspend fun downloadFile(fileId: String, url: String, mimeType: String) {
        println("Downloading file from repo $url with mimeType $mimeType")
        val data = remoteDataSource.downloadFile(url)
        when (data) {
            is Result.Error -> {
                println(data.message)
            }

            Result.Loading -> {
                //TODO
            }

            is Result.Success -> {
                val localPath =
                    fileManager.saveFile(data.data.data, data.data.fileName, mimeType)
                localDataSource.insertFile(
                    MaterialFile(
                        id = fileId,
                        localPath = localPath
                    )
                )
                fileManager.openFile(localPath, mimeType)
            }
        }
    }

    override suspend fun openFile(fileId: String, url: String, mimeType: String) {
        if (getPlatform() != Platform.JS)
            localDataSource.getFilePath(fileId).collect { result ->
                if (result is Result.Success) {
                    fileManager.openFile(result.data.localPath, mimeType)
                } else {
                    val data = remoteDataSource.downloadFile(url)
                    when (data) {
                        is Result.Error -> {
                            println(data.message)
                        }

                        Result.Loading -> {
                            //TODO
                        }

                        is Result.Success -> {
                            val localPath =
                                fileManager.saveFile(data.data.data, data.data.fileName, mimeType)
                            localDataSource.insertFile(
                                MaterialFile(
                                    id = fileId,
                                    localPath = localPath
                                )
                            )
                            fileManager.openFile(localPath, mimeType)
                        }
                    }
                }
            }

    }

    override fun renameFolder(
        folderId: String,
        newName: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>> {
        return remoteDataSource.renameFolder(folderId, newName)
    }


    override suspend fun openLink(url: String) {
        if (getPlatform() != Platform.JS) {
            fileManager.openLink(url)
        }
    }
}

