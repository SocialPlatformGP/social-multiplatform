package com.gp.socialapp.data.chat.source.local.model

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.util.LocalDateTimeUtil.now
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import korlibs.time.DateFormat
import korlibs.time.parse
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class MessageEntity (): RealmObject {
    var content: String = ""
    var createdAt: String = ""
    var roomId: Long = 0L
    var senderId: String = ""
    @PrimaryKey var id: Long = 0L
    var senderName: String = ""
    var senderPfpURL: String = ""
    var hasAttachment: Boolean = false
    var attachmentUrl: String = ""
    var attachmentName: String = ""
    var attachmentType: String = ""
    var attachmentSize: Long = 0
    constructor(
        content: String = "",
        createdAt: String = "",
        roomId: Long = 0L,
        senderId: String = "",
        id: Long = 0L,
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
        val formatter = DateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
        val createdAt = formatter.parse(this.createdAt)
        return Message(
            content = content,
            createdAt = createdAt,
            roomId = roomId,
            senderId = senderId,
            id = id,
            senderName = senderName,
            senderPfpUrl = senderPfpURL,
            hasAttachment = hasAttachment,
            attachment = MessageAttachment(
                byteArray = byteArrayOf(),
                url = attachmentUrl,
                name = attachmentName,
                type = attachmentType,
                size =attachmentSize
            )
        )
    }
    companion object {
        fun Message.toEntity(): MessageEntity {
            val createdAt = this.createdAt.format("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
            return MessageEntity(
                content = this.content,
                createdAt = createdAt,
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