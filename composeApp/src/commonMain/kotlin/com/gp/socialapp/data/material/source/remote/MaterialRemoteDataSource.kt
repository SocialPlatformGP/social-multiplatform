package com.gp.socialapp.data.material.source.remote

import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface MaterialRemoteDataSource {
    suspend fun getMaterialAtPath(
        path: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses>>

    suspend fun createFolder(
        name: String,
        path: String,
    ): Flow<Result<MaterialResponse.GetMaterialResponses>>

    suspend fun createFile(
        name: String,
        type: String,
        path: String,
        content: ByteArray
    ): Flow<Result<MaterialResponse.GetMaterialResponses>>

    suspend fun deleteFile(
        fileId: String,
        path: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses>>

    suspend fun deleteFolder(folderId: String): Flow<Result<MaterialResponse.GetMaterialResponses>>
    suspend fun downloadFile(url: String): Result<MaterialResponse.DownloadFileResponse>

}