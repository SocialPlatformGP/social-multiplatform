package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.Room
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    suspend fun createGroupRoom(
        groupName: String,
        groupAvatar: ByteArray,
        userIds: List<String>,
        creatorId: String
    ): Flow<Result<Room>>

    suspend fun checkIfRoomExists(
        user1: String,
        user2: String
    ): Flow<Result<Room?>>
    suspend fun getRoomDetails(
        roomId: String
    ): Result<Room>

    suspend fun updateRoomAvatar(
        roomId: String, byteArray: ByteArray
    ): Result<String>

    suspend fun updateRoomName(
        roomId: String, name: String
    ): Result<Nothing>

    suspend fun addMembers(
        roomId: String, userIds: List<String>
    ): Result<Nothing>

    suspend fun removeMember(
        roomId: String, userId: String
    ): Result<Nothing>
}