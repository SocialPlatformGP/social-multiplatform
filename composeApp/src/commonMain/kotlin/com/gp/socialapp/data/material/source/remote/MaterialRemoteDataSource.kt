package com.gp.socialapp.data.material.source.remote

import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

interface MaterialRemoteDataSource {
    suspend fun downloadChatAttachment(
        path: String,
    ): Results<MaterialResponse.DownloadChatAttachment, DataError.Network>
    suspend fun getMaterialAtPath(
        path: String
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>>

    suspend fun createFolder(
        name: String,
        path: String,
        communityId: String,
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>>

    suspend fun createFile(
        name: String,
        type: String,
        path: String,
        content: ByteArray,
        communityId: String
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>>

    suspend fun deleteFile(
        fileId: String,
        path: String
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>>

    suspend fun deleteFolder(folderId: String): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>>
    suspend fun downloadFile(url: String): Results<MaterialResponse.DownloadFileResponse, DataError.Network>
    fun renameFolder(
        folderId: String,
        newName: String
    ): Flow<Results<MaterialResponse.GetMaterialResponses, DataError.Network>>

}

