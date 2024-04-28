package com.gp.socialapp.presentation.chat.createprivate

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.Room

data class CreatePrivateChatUiState(
    val allUsers: List<User> = emptyList(),
    val matchingUsers: List<User> = emptyList(),
    val currentUserId: String = "",
    val room: Room? = null,
)