package com.gp.socialapp.data.calendar.model

import com.gp.socialapp.data.auth.source.remote.model.User
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class CalendarEvent(
    val dateTime: LocalDateTime,
    val title: String,
    val type: String,
    val description: String,
)

