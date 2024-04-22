package com.gp.socialapp.data.material.repository

import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    suspend fun getMaterialAtPath(
        path: String
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>>

    suspend fun createFolder(
        name: String,
        path: String,
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>>

    suspend fun createFile(
        name: String,
        type: String,
        path: String,
        content: ByteArray
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>>

    suspend fun deleteFile(
        fileId: String,
        path: String
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>>

    suspend fun deleteFolder(
        folderId: String
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>>

    suspend fun downloadFile(url: String, mimeType: String)
    suspend fun openFile(fileId: String, url: String, mimeType: String)
    suspend fun shareLink(url: String)
    suspend fun openLink(url: String)

}

