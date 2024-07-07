package com.gp.socialapp.presentation.calendar.createevent

import com.gp.socialapp.data.auth.source.remote.model.User

data class CreateEventUiState(
    val currentUser: User = User(),
    val title: String = "",
    val description: String = "",
    val date: Long = 0L,
    val time: Long = 0L,
    val isCreated: Boolean = false,
    val location : String = "",
    val type : String = "Event",
    val communityId: String = "",
    val isPrivate : Boolean = true
)
