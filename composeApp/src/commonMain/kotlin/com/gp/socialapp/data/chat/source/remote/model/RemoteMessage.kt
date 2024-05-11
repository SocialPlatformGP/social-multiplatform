package com.gp.socialapp.data.chat.source.remote.model

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import korlibs.time.DateFormat
import korlibs.time.parse
import kotlinx.serialization.Serializable

@Serializable
data class RemoteMessage(
    val content: String = "",
    val createdAt: String = "",
    val roomId: Long = 0L,
    val senderId: String = "",
    val id: Long = 0L,
    val senderName: String = "",
    val senderPfpUrl: String = "",
    val hasAttachment: Boolean = false,
    val attachment: MessageAttachment = MessageAttachment()
) {
    fun toMessage(): Message {
        val formatter = DateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
        val createdAt = formatter.parse(this.createdAt)
        return Message(
            content = content,
            createdAt = createdAt,
            roomId = roomId,
            senderId = senderId,
            id = id,
            senderName = senderName,
            senderPfpUrl = senderPfpUrl,
            hasAttachment = hasAttachment,
            attachment = attachment
        )
    }

    companion object {
        fun fromMessage(message: Message): RemoteMessage {
            val createdAt = message.createdAt.format("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
            return RemoteMessage(
                content = message.content,
                createdAt = createdAt,
                roomId = message.roomId,
                senderId = message.senderId,
                id = message.id,
                senderName = message.senderName,
                senderPfpUrl = message.senderPfpUrl,
                hasAttachment = message.hasAttachment,
                attachment = message.attachment
            )
        }
    }
}
