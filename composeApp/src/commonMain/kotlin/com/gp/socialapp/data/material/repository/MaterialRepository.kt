package com.gp.socialapp.data.material.repository

import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
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

    suspend fun deleteFolder(
        folderId: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses>>

    suspend fun downloadFile(url: String, mimeType: String)
    suspend fun openFile(fileId: String, url: String, mimeType: String)
    suspend fun shareLink(url: String)

}