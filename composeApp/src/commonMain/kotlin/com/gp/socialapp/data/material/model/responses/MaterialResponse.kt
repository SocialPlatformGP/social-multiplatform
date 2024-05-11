package com.gp.socialapp.data.material.model.responses

import com.gp.socialapp.data.material.model.MaterialFile
import com.gp.socialapp.data.material.model.MaterialFolder
import kotlinx.serialization.Serializable

@Serializable
sealed class MaterialResponse {
    @Serializable
    data class GetMaterialResponses(
        val files: List<MaterialFile> = emptyList(),
        val folders: List<MaterialFolder> = emptyList()
    ) : MaterialResponse()

    @Serializable
    data class DownloadFileResponse(
        val data: ByteArray,
        val fileName: String
    ) : MaterialResponse()
    @Serializable
    data class DownloadChatAttachment(
        val data: ByteArray,
    ) : MaterialResponse()
}