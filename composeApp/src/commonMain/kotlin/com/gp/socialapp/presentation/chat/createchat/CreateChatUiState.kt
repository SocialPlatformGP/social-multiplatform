package com.gp.socialapp.presentation.chat.createchat

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.data.chat.model.Room

data class CreateChatUiState(
    val allUsers: List<User> = emptyList(),
    val matchingUsers: List<User> = emptyList(),
    val currentUser: User = User(),
    val room: Room? = null,
    val recentRoom: RecentRoom? = null
)