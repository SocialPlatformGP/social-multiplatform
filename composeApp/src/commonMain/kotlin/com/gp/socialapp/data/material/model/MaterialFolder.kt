package com.gp.socialapp.data.material.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MaterialFolder(
    val name: String = "",
    val createdAt: LocalDateTime? = null,
    val id: String = "",
    val path: String = ""
)