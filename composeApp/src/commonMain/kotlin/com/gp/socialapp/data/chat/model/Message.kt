package com.gp.socialapp.data.chat.model

import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val content: String = "",
    val createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds,
    val roomId: String = "",
    val senderId: String = "",
    val id: String = "",
    val senderName: String = "",
    val senderPfpURL: String = "",
    val hasAttachment: Boolean = false,
    val attachment: MessageAttachment = MessageAttachment()
)