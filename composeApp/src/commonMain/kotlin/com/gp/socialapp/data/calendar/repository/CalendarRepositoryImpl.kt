package com.gp.socialapp.data.calendar.repository

import com.gp.socialapp.data.calendar.model.CalendarEvent
import com.gp.socialapp.data.calendar.source.remote.CalendarRemoteDataSource
import com.gp.socialapp.data.calendar.source.remote.model.CalendarRequest
import com.gp.socialapp.util.CalendarError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class CalendarRepositoryImpl(
    private val calendarRemoteDataSource: CalendarRemoteDataSource,
) : CalendarRepository {
    override fun getUserEvents(userId: String): Flow<Result<List<CalendarEvent>,CalendarError.GetEvents>> {
        val request = CalendarRequest.GetUserEvents(userId)
        return calendarRemoteDataSource.getUserEvents(request)
    }

    override suspend fun createUserEvent(userId: String, event: CalendarEvent): Result<Unit,CalendarError.CreateEvent> {
        val request = CalendarRequest.CreateEvent(userId, event)
        return calendarRemoteDataSource.createUserEvent(request)
    }

}