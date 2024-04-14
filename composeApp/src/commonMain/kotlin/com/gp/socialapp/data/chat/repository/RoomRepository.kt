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
    ): Flow<Result<String>>

    suspend fun checkIfRoomExists(
        user1: String,
        user2: String
    ): Flow<Result<Room?>>
}