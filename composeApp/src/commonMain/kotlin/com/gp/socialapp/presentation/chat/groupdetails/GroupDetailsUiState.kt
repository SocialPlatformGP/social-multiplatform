package com.gp.socialapp.presentation.chat.groupdetails

import com.gp.socialapp.data.auth.source.remote.model.User

data class GroupDetailsUiState(
    val groupName: String = "",
    val groupAvatarUrl: String = "",
    val members: List<User> = emptyList(),
    val admins: List<String> = emptyList(),
    val currentUserId: String = "",
    val isAdmin: Boolean = false,
)
