package com.gp.socialapp.data.chat.model

import korlibs.time.DateTimeTz


data class Message(
    val content: String = "",
    val createdAt: DateTimeTz = DateTimeTz.nowLocal(),
    val roomId: Long = 0L,
    val senderId: String = "",
    val id: Long = 0L,
    val senderName: String = "",
    val senderPfpUrl: String = "",
    val hasAttachment: Boolean = false,
    val attachment: MessageAttachment = MessageAttachment()
)
