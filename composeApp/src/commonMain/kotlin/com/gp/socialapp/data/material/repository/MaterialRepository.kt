package com.gp.socialapp.data.material.repository

import com.gp.socialapp.data.material.model.responses.MaterialResponse
import com.gp.socialapp.util.MaterialError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    suspend fun getMaterialAtPath(
        path: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError.GetMaterial>>

    suspend fun createFolder(
        name: String,
        path: String,
        communityId: String,
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError.CreateFolder>>

    suspend fun createFile(
        name: String,
        type: String,
        path: String,
        content: ByteArray,
        communityId: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError.CreateFile>>

    suspend fun deleteFile(
        fileId: String,
        path: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError.DeleteFile>>

    suspend fun deleteFolder(
        folderId: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError.DeleteFolder>>

    suspend fun downloadFile(url: String, mimeType: String)
    suspend fun openFile(fileId: String, url: String, mimeType: String)
    suspend fun shareLink(url: String)
    suspend fun openLink(url: String)
    fun renameFolder(
        folderId: String,
        newName: String
    ): Flow<Result<MaterialResponse.GetMaterialResponses, MaterialError.RenameFolder>>

}

