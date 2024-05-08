package com.gp.socialapp.presentation.calendar.createevent

import com.gp.socialapp.data.auth.source.remote.model.User

data class CreateEventUiState(
    val currentUser: User = User(),
    val title: String = "",
    val description: String = "",
    val date: Long = 0L,
    val isCreated: Boolean = false
)
