package com.gp.socialapp.data.calendar.source.remote.model

import com.gp.socialapp.data.calendar.model.CalendarEvent
import kotlinx.serialization.Serializable

sealed interface CalendarRequest {
    @Serializable
    data class GetUserEvents(
        val userId: String
    ) : CalendarRequest
    @Serializable
    data class CreateEvent(
        val userId: String,
        val event: CalendarEvent
    )
}