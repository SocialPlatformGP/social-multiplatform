package com.gp.socialapp.data.material.model

import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MaterialFile(
    val communityId: String = "",
    val name: String = "",
    val size: String = "",
    val type: String = "",
    val url: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val id: String = "",
    val path: String = "",
    val localPath: String = "",
)