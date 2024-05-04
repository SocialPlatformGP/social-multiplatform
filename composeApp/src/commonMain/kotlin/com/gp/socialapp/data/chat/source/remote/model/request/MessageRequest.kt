package com.gp.socialapp.data.chat.source.remote.model.request

import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable

sealed class MessageRequest {
    @Serializable
    data class FetchMessages(val roomId: String, val timestamp: Long = 0) : MessageRequest()

    @Serializable
    data class SendMessage(
        val content: String = "",
        val roomId: String = "",
        val senderId: String = "",
        val hasAttachment: Boolean = false,
        val attachment: MessageAttachment = MessageAttachment()
    ) : MessageRequest()

    @Serializable
    data class UpdateMessage(
        val messageId: String,
        val roomId: String,
        val updatedContent: String
    ) : MessageRequest()
    @Serializable
    data class DeleteMessage(
        val messageId: String,
        val roomId: String
    ) : MessageRequest()
    @Serializable
    data class ReportMessage(
        val messageId: String,
        val roomId: String,
        val reporterId: String
    ) : MessageRequest()
}