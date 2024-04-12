package com.gp.socialapp.presentation.chat.creategroup

import com.gp.socialapp.data.auth.source.remote.model.User

data class CreateGroupUiState(
    val groupName: String = "",
    val isCreated: Boolean = false,
    val groupId: String = "",
    val groupAvatar: ByteArray = byteArrayOf(),
    val allUsers: List<User> = emptyList(),
    val selectedUsers: List<User> = emptyList(),
)
