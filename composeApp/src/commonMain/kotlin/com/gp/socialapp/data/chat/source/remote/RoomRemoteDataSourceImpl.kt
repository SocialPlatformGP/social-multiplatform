package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Room
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


    override suspend fun createGroupRoom(request: RoomRequest.CreateGroupRoom): Flow<Result<Room>> =
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
                    val body = response.body<RoomResponse.CreateGroupRoom>()
                    val room = Room(
                        id = body.roomId,
                        picUrl = body.roomAvatarUrl
                    )
                    emit(Result.SuccessWithData(room))
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

    override suspend fun getRoomDetails(request: RoomRequest.GetRoomDetails): Result<Room> {
        return try {
            val response = httpClient.post {
                endPoint("getRoomDetails")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                val room = response.body<RoomResponse.GetRoomDetails>().room
                Result.SuccessWithData(room)
            } else {
                Result.Error("An error occurred: ${response.status.description}")
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }

    override suspend fun updateRoomAvatar(request: RoomRequest.UpdateRoomAvatar): Result<String> {
        return try {
            val response = httpClient.post {
                endPoint("updateRoomAvatar")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                val roomId = response.body<RoomResponse.UpdateRoomAvatar>().avatarUrl
                Result.SuccessWithData(roomId)
            } else {
                Result.Error("An error occurred: ${response.status.description}")
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }

    override suspend fun updateRoomName(request: RoomRequest.UpdateRoomName): Result<Nothing> {
        return try {
            val response = httpClient.post {
                endPoint("updateRoomName")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                Result.Success
            } else {
                Result.Error("An error occurred: ${response.status.description}")
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }

    override suspend fun addMembers(request: RoomRequest.AddMembers): Result<Nothing> {
        return try {
            val response = httpClient.post {
                endPoint("addMembers")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                Result.Success
            } else {
                Result.Error("An error occurred: ${response.status.description}")
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }

    override suspend fun removeMember(request: RoomRequest.RemoveMember): Result<Nothing> {
        return try {
            val response = httpClient.post {
                endPoint("removeMember")
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                Result.Success
            } else {
                Result.Error("An error occurred: ${response.status.description}")
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }
}