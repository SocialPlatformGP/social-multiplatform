package com.gp.socialapp.presentation.chat.private_chat

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.Room

data class CreatePrivateChatUiState(
    val users: List<User> = emptyList(),
    val currentUser: String = "",
    val room: Room? = null,
)