package com.gp.socialapp.data.chat.model

@kotlinx.serialization.Serializable
data class RecentRoom(
    val roomId: String = "",
    val lastMessage: String = "",
    val isPrivate: Boolean = true,
    val sender_id: String = "",
    val receiver_id: String = "",
    val pic_url: String = "", //if the room is a group chat, this will be the group chat image
    val title: String = "", //if the room is a group chat, this will be the group chat name
    val lastMessageTime: Long = 0,
)

@kotlinx.serialization.Serializable
data class RecentRoomResponse(
    val roomId: String,
    val lastMessage: String,
    val isPrivate: Boolean,
    val senderName: String,
    val receiverName: String,
    val senderPicUrl: String,
    val receiverPicUrl: String,
    val pic_url: String, //if the room is a group chat, this will be the group chat image
    val title: String, //if the room is a group chat, this will be the group chat name
    val lastMessageTime: Long,
)