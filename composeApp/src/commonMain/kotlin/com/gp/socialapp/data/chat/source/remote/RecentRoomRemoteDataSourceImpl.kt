package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.source.remote.model.request.RecentRoomRequests
import com.gp.socialapp.data.chat.source.remote.model.response.RecentRoomResponses
import com.gp.socialapp.data.chat.utils.EndPoint
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.flow

class RecentRoomRemoteDataSourceImpl(
    private val client: HttpClient
) : RecentRoomRemoteDataSource {
    override fun getAllRecentRooms(userId: String) = flow {
        println("Id: $userId")
        emit(Result.Loading)
        try {
            val response = client.post {
                endPoint(EndPoint.GetAllRecentRooms.url)
                setBody(RecentRoomRequests.GetAllRecentRooms(userId))
            }
            println("response: $response")
            if (response.status == HttpStatusCode.OK) {
                val result = response.body<RecentRoomResponses.GetAllRecentRooms>()
                emit(Result.SuccessWithData(result.recentRooms))
            } else {
                emit(Result.Error("An error occurred"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
        }
    }
}