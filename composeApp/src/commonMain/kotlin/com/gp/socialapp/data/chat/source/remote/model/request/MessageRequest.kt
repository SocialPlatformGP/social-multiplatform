package com.gp.socialapp.data.chat.source.remote.model.request

import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable

sealed class MessageRequest {
    @Serializable
    data class FetchMessages(val roomId: String) : MessageRequest()

    @Serializable
    data class SendMessage(
        val content: String = "",
        val roomId: String = "",
        val senderId: String = "",
        val hasAttachment: Boolean = false,
        val attachment: MessageAttachment = MessageAttachment()
    ) : MessageRequest()
}