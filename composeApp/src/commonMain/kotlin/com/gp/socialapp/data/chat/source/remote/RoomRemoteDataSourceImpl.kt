package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.source.remote.model.request.RoomRequest
import com.gp.socialapp.data.chat.source.remote.model.response.RoomResponse
import com.gp.socialapp.data.chat.utils.EndPoint
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RoomRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : RoomRemoteDataSource {
    override suspend fun createGroupRoom(request: RoomRequest.CreateGroupRoom): Flow<Result<String>> =
        flow {
            emit(Result.Loading)
            try {
                val response = httpClient.post {
                    endPoint(EndPoint.CreateGroupRoom.url)
                    setBody(
                        request
                    )
                }
                if (response.status == HttpStatusCode.OK) {
                    val roomId = response.body<RoomResponse.CreateGroupRoom>().roomId
                    emit(Result.SuccessWithData(roomId))
                } else {
                    emit(Result.Error("An error occurred: ${response.status.description}"))
                }
            } catch (e: Exception) {
                emit(Result.Error("An error occurred: ${e.message}"))
            }
        }

    override suspend fun checkIfRoomExists(
        user1: String,
        user2: String
    ) = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.post {
                endPoint(EndPoint.CheckIfRoomExists.url)
                setBody(
                    RoomRequest.RoomExistRequest(user1, user2)
                )
            }
            if (response.status == HttpStatusCode.OK) {
                println("Response: ${response.status}")
                val room = response.body<RoomResponse.CheckIfRoomExists>().room
                println("Room: $room")
                emit(Result.SuccessWithData(room))
            } else {
                emit(Result.Error("An error occurred: ${response.status.description}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("An error occurred: ${e.message}"))
        }
    }
}