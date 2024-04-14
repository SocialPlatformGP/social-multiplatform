package com.gp.socialapp.presentation.chat.chatroom

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment

data class ChatRoomUiState(
    val messages: List<Message> = emptyList(),
    val currentUserId: String = "",
    val currentAttachment: MessageAttachment = MessageAttachment(),
)