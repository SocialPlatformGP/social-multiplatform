package com.gp.socialapp.data.calendar.repository

import com.gp.socialapp.data.calendar.model.CalendarEvent
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {
    fun getUserEvents(userId: String): Flow<Result<List<CalendarEvent>>>
    suspend fun createUserEvent(userId: String, event: CalendarEvent): Result<Nothing>
}