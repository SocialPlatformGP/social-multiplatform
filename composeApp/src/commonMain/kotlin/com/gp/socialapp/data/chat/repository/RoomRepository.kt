package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.Room
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    suspend fun createGroupRoom(
        groupName: String,
        groupAvatar: ByteArray,
        userIds: List<String>,
        creatorId: String
    ): Flow<Result<Room,ChatError.Temp>>

    suspend fun checkIfRoomExists(
        user1: String,
        user2: String
    ): Flow<Result<Room?,ChatError.Temp>>
    suspend fun getRoomDetails(
        roomId: String
    ): Result<Room,ChatError.Temp>

    suspend fun updateRoomAvatar(
        roomId: String, byteArray: ByteArray
    ): Result<String,ChatError.Temp>

    suspend fun updateRoomName(
        roomId: String, name: String
    ): Result<Unit,ChatError.Temp>

    suspend fun addMembers(
        roomId: String, userIds: List<String>
    ): Result<Unit,ChatError.Temp>

    suspend fun removeMember(
        roomId: String, userId: String
    ): Result<Unit,ChatError.Temp>
}