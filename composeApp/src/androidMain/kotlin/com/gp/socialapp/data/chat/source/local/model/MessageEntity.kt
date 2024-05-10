package com.gp.socialapp.data.chat.source.local.model

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.util.LocalDateTimeUtil.now
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class MessageEntity (): RealmObject {
    var content: String = ""
    var createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds
    var roomId: String = ""
    var senderId: String = ""
    @PrimaryKey
    var id: String = ""
    var senderName: String = ""
    var senderPfpURL: String = ""
    var hasAttachment: Boolean = false
    var attachmentUrl: String = ""
    var attachmentName: String = ""
    var attachmentType: String = ""
    var attachmentSize: Long = 0
    constructor(
        content: String = "",
        createdAt: Long = 0L,
        roomId: String = "",
        senderId: String = "",
        id: String = "",
        senderName: String = "",
        senderPfpURL: String = "",
        hasAttachment: Boolean = false,
        attachment: MessageAttachment = MessageAttachment()
    ) : this() {
        this.id = id
        this.content = content
        this.createdAt = createdAt
        this.roomId = roomId
        this.senderId = senderId
        this.senderName = senderName
        this.senderPfpURL = senderPfpURL
        this.hasAttachment = hasAttachment
        this.attachmentUrl = attachment.url
        this.attachmentName = attachment.name
        this.attachmentType = attachment.type
        this.attachmentSize = attachment.size
    }
    fun toMessage(): Message {
        return Message(
            content = content,
            createdAt = createdAt,
            roomId = roomId,
            senderId = senderId,
            id = id,
            senderName = senderName,
            senderPfpUrl = senderPfpURL,
            hasAttachment = hasAttachment,
            attachment = MessageAttachment(byteArrayOf(), attachmentUrl, attachmentName, attachmentType, attachmentSize)
        )
    }
    companion object {
        fun Message.toEntity(): MessageEntity {
            return MessageEntity(
                content = this.content,
                createdAt = this.createdAt,
                roomId = this.roomId,
                senderId = this.senderId,
                id = this.id,
                senderName = this.senderName,
                senderPfpURL = this.senderPfpUrl,
                hasAttachment = this.hasAttachment,
                attachment = this.attachment
            )
        }
    }
}