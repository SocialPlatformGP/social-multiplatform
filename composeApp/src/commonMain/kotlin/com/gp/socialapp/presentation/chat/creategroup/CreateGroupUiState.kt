package com.gp.socialapp.presentation.chat.creategroup

import com.gp.socialapp.data.auth.source.remote.model.User

data class CreateGroupUiState(
    val groupName: String = "",
    val isCreated: Boolean = false,
    val isError: Boolean = false,
    val groupId: String = "",
    val groupAvatar: ByteArray = byteArrayOf(),
    val allUsers: List<SelectableUser> = emptyList(),
    val selectedUsers: List<User> = emptyList(),
)
