package com.gp.socialapp.presentation.chat.chatroom

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.model.Room

data class ChatRoomUiState(
    val messages: List<Message> = emptyList(),
    val currentUser: User = User(),
    val currentRoom: Room = Room(),
    val currentAttachment: MessageAttachment = MessageAttachment(),
    val isMessagesLoading: Boolean = false,
    val isSendLoading: Boolean = false,
)