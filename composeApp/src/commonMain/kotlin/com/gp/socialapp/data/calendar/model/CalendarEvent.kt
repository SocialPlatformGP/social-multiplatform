package com.gp.socialapp.data.calendar.model

import com.gp.socialapp.data.auth.source.remote.model.User
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class CalendarEvent(
    val date: Long=0L,
    val title: String = "",
    val time : Long = 0L,
    val type: String = "Event",
    val description: String = "",
    val location : String = "Online",
    val user: String = "",
    val communityId: String = "",
    val isPrivate: Boolean = true,

)

