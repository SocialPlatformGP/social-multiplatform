package com.gp.socialapp.data.calendar.source.remote

import com.gp.socialapp.data.calendar.model.CalendarEvent
import com.gp.socialapp.data.calendar.source.remote.model.CalendarRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.Result
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
    override fun getUserEvents(request: CalendarRequest.GetUserEvents): Flow<Result<List<CalendarEvent>>> = flow {
        emit(Result.Loading)
        try{
            val response = httpClient.post {
                endPoint("getUserEvents")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                val data = response.body<List<CalendarEvent>>()
                emit(Result.SuccessWithData(data))
            } else {
                emit(Result.Error("Server error: ${response.status.value} ${response.status.description}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message?: "An error occurred"))
        }
    }

    override suspend fun createUserEvent(request: CalendarRequest.CreateEvent): Result<Nothing> {
        return try{
            val response = httpClient.post {
                endPoint("createUserEvent")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                Result.Success
            } else {
                Result.Error("Server error: ${response.status.value} ${response.status.description}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message?: "An error occurred")
        }
    }

}