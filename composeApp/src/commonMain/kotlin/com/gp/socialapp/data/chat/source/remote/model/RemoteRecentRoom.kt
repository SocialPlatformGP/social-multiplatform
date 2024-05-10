package com.gp.socialapp.data.chat.source.remote.model

import com.gp.socialapp.data.chat.model.RecentRoom
import korlibs.time.DateFormat
import korlibs.time.parse
import kotlinx.serialization.Serializable

@Serializable
data class RemoteRecentRoom(
    val roomId: Long = 0L,
    val lastMessage: String = "",
    val isPrivate: Boolean = true,
    val senderId: String = "",
    val senderName: String = "",
    val senderPicUrl: String = "",
    val receiverId: String = "",
    val receiverName: String = "",
    val receiverPicUrl: String = "",
    val lastMessageTime: String = "",
    val lastMessageId: Long = 0L,
    val lastMessageSenderName: String = ""
) {
    fun toRecentRoom(): RecentRoom {
        val formatter = DateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
        val lastMessageTime = formatter.parse(this.lastMessageTime)
        return RecentRoom(
            roomId = roomId,
            lastMessage = lastMessage,
            isPrivate = isPrivate,
            senderId = senderId,
            senderName = senderName,
            senderPicUrl = senderPicUrl,
            receiverId = receiverId,
            receiverName = receiverName,
            receiverPicUrl = receiverPicUrl,
            lastMessageTime = lastMessageTime,
            lastMessageId = lastMessageId,
            lastMessageSenderName = lastMessageSenderName
        )
    }

    companion object {
        fun fromRecentRoom(recentRoom: RecentRoom): RemoteRecentRoom {
            val lastMessageTime = recentRoom.lastMessageTime.format("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
            return RemoteRecentRoom(
                roomId = recentRoom.roomId,
                lastMessage = recentRoom.lastMessage,
                isPrivate = recentRoom.isPrivate,
                senderId = recentRoom.senderId,
                senderName = recentRoom.senderName,
                senderPicUrl = recentRoom.senderPicUrl,
                receiverId = recentRoom.receiverId,
                receiverName = recentRoom.receiverName,
                receiverPicUrl = recentRoom.receiverPicUrl,
                lastMessageTime = lastMessageTime,
                lastMessageId = recentRoom.lastMessageId,
                lastMessageSenderName = recentRoom.lastMessageSenderName
            )
        }
    }
}
