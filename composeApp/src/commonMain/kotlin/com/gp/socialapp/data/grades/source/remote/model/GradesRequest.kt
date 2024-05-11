package com.gp.socialapp.data.grades.source.remote.model

import kotlinx.serialization.Serializable

sealed interface GradesRequest {
    @Serializable
    data class UploadGradesFile(
        val name: String = "",
        val type: String = "",
        val content: ByteArray = byteArrayOf(),
        val subject : String = "",
        val communityId: String = "",
    ): GradesRequest

    @Serializable
    data class GetGrades(
        val userName : String = "",
    ): GradesRequest
}