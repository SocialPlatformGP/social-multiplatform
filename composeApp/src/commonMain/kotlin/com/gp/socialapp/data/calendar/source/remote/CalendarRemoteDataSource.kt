package com.gp.socialapp.data.calendar.source.remote

import com.gp.socialapp.data.calendar.model.CalendarEvent
import com.gp.socialapp.data.calendar.source.remote.model.CalendarRequest
import com.gp.socialapp.util.CalendarError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface CalendarRemoteDataSource {
    fun getUserEvents(request: CalendarRequest.GetUserEvents): Flow<Result<List<CalendarEvent>, CalendarError.GetEvents>>
    suspend fun createUserEvent(request: CalendarRequest.CreateEvent): Result<Unit, CalendarError.CreateEvent>
}