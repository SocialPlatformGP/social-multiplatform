package com.gp.socialapp.data.chat.model

import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val id: String = "",
    val name: String = "",
    val picUrl: String = "",
    val members: Map<String,Boolean> = emptyMap(), //userId to isAdmin
    val isPrivate: Boolean = true,
    val createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds,
    val bio: String = "", //group chat description
)