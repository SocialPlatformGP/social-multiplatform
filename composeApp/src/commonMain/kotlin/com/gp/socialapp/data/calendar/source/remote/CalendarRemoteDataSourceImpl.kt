package com.gp.socialapp.data.calendar.source.remote

import com.gp.socialapp.data.calendar.model.CalendarEvent
import com.gp.socialapp.data.calendar.source.remote.model.CalendarRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.CalendarError
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Result.Companion.success
import io.github.jan.supabase.SupabaseClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CalendarRemoteDataSourceImpl(
    private val supabaseClient: SupabaseClient,
    private val httpClient: HttpClient
) : CalendarRemoteDataSource {
    override fun getUserEvents(request: CalendarRequest.GetUserEvents): Flow<Result<List<CalendarEvent>, CalendarError.GetEvents>> = flow {
        emit(Result.Loading)
        try{
            val response = httpClient.post {
                endPoint("getUserEvents")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                val data = response.body<List<CalendarEvent>>()
                emit(success(data))
            } else {
                val serverError = response.body<CalendarError.GetEvents>()
                emit(Result.Error(serverError))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(CalendarError.GetEvents.SERVER_ERROR))
        }
    }

    override suspend fun createUserEvent(request: CalendarRequest.CreateEvent): Result<Unit,CalendarError.CreateEvent> {
        return try{
            val response = httpClient.post {
                endPoint("createUserEvent")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val serverError = response.body<CalendarError.CreateEvent>()
                error(serverError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(CalendarError.CreateEvent.SERVER_ERROR)
        }
    }

}