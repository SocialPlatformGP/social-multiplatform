package com.gp.socialapp.presentation.chat.addmembers

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.chat.creategroup.SelectableUser

data class AddMembersUiState(
    val selectedUsers: List<User> = emptyList(),
    val allUsers: List<SelectableUser> = emptyList(),
    val isDone: Boolean = false
)
