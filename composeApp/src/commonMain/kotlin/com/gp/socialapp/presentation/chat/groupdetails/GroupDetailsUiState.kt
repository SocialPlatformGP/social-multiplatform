package com.gp.socialapp.presentation.chat.groupdetails

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.data.chat.model.Room

data class GroupDetailsUiState(
    val groupName: String = "",
    val groupAvatarUrl: String = "",
    val members: List<User> = emptyList(),
    val admins: List<String> = emptyList(),
    val currentUser: User = User(),
    val isAdmin: Boolean = false,
    val privateRoom: Room? = null,
    val privateRecentRoom: RecentRoom? = null
)
