package com.gp.socialapp.data.material.model.requests

import kotlinx.serialization.Serializable

sealed class MaterialRequest {
    @Serializable
    data class GetMaterialRequest(
        val path: String = ""
    ) : MaterialRequest()

    @Serializable
    data class CreateFolderRequest(
        val name: String = "",
        val path: String = "",
        val communityId: String,
    )

    @Serializable
    data class CreateFileRequest(
        val name: String = "",
        val type: String = "",
        val content: ByteArray = byteArrayOf(),
        val path: String = "",
        val communityId: String,
    )

    @Serializable
    data class DeleteFileRequest(
        val fileId: String = "",
        val path: String = ""
    ) : MaterialRequest()

    @Serializable
    data class DownloadFileRequest(val url: String) : MaterialRequest()
}