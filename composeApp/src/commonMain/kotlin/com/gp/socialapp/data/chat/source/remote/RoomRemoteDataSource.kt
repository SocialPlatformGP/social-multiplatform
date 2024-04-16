package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Room
import com.gp.socialapp.data.chat.source.remote.model.request.RoomRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface RoomRemoteDataSource {
    suspend fun createGroupRoom(request: RoomRequest.CreateGroupRoom): Flow<Result<Room>>
    suspend fun checkIfRoomExists(user1: String, user2: String): Flow<Result<Room?>>
    suspend fun getRoomDetails(
        request: RoomRequest.GetRoomDetails
    ): Result<Room>

    suspend fun updateRoomAvatar(
        request: RoomRequest.UpdateRoomAvatar
    ): Result<String>

    suspend fun updateRoomName(
        request: RoomRequest.UpdateRoomName
    ): Result<Nothing>

    suspend fun addMembers(
        request: RoomRequest.AddMembers
    ): Result<Nothing>

    suspend fun removeMember(
        request: RoomRequest.RemoveMember
    ): Result<Nothing>
}