package com.gp.socialapp.presentation.chat.creategroup

import com.gp.socialapp.data.auth.source.remote.model.User

data class CreateGroupUiState(
    val groupName: String = "",
    val isCreated: Boolean = false,
    val isError: Boolean = false,
    val groupId: String = "",
    val groupAvatarByteArray: ByteArray = byteArrayOf(),
    val groupAvatarUrl: String = "",
    val allUsers: List<SelectableUser> = emptyList(),
    val selectedUsers: List<User> = emptyList(),
)
