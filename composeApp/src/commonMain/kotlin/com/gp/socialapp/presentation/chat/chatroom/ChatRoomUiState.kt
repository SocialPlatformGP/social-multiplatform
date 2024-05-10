package com.gp.socialapp.presentation.chat.chatroom

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment

data class ChatRoomUiState(
    val messages: List<Message> = emptyList(),
    val currentUser: User = User(),
    val currentAttachment: MessageAttachment = MessageAttachment(),
)