package com.gp.socialapp.presentation.calendar.home

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.calendar.model.CalendarEvent
import kotlinx.datetime.Month

data class CalendarHomeUiState(
    val currentUser: User = User(),
    val events: List<CalendarEvent> = emptyList(),
)
