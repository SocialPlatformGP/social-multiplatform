package com.gp.socialapp.data.material.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MaterialFile(
    val name: String = "",
    val type: String = "",
    val url: String = "",
    val createdAt: LocalDateTime? = null,
    val id: String = "",
    val path: String = "",
    val localPath: String = "",
)