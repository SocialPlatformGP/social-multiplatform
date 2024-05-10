package com.gp.socialapp.data.chat.model

import korlibs.time.DateTimeTz


data class RecentRoom(
    val roomId: Long = 0L,
    val lastMessage: String = "",
    val isPrivate: Boolean = true,
    val senderId: String = "",
    val senderName: String = "",
    val senderPicUrl: String = "",
    val receiverId: String = "",
    val receiverName: String = "",
    val receiverPicUrl: String = "",
    val lastMessageTime: DateTimeTz = DateTimeTz.nowLocal(),
    val lastMessageId: Long = 0L,
)

