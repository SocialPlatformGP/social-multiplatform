package com.gp.socialapp.data.material.source.remote

import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.MaterialError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface MaterialRemoteDataSource {
    suspend fun downloadChatAttachment(
        path: String,
    ): Result<MaterialResponse.DownloadChatAttachment, ChatError>
    suspend fun getMaterialAtPath(
        communityId: String,
        path: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>>

    suspend fun createFolder(
        name: String,
        path: String,
        communityId: String,
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>>

    suspend fun createFile(
        name: String,
        type: String,
        path: String,
        content: ByteArray,
        communityId: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>>

    suspend fun deleteFile(
        fileId: String,
        path: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>>

    suspend fun deleteFolder(folderId: String): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>>
    suspend fun downloadFile(url: String): Result<MaterialResponse.DownloadFileResponse, MaterialError>
    fun renameFolder(
        folderId: String,
        newName: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError>>

}

