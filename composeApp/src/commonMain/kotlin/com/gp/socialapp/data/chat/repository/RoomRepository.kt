package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    suspend fun createGroupRoom(
        groupName: String,
        groupAvatar: ByteArray,
        userIds: List<String>,
        creatorId: String
    ): Flow<Result<String>>
}