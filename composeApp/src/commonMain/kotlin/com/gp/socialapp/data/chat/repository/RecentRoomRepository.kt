package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.RecentRoomResponse
import com.gp.socialapp.data.chat.source.remote.model.response.NewDataResponse
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface RecentRoomRepository {
    fun getAllRecentRooms(userId: String): Flow<Result<List<RecentRoomResponse>,ChatError.Temp>>
    suspend fun connectToSocket(currentUserId: String): Result<Unit,ChatError.Temp>
    suspend fun observeNewData(): Flow<Result<NewDataResponse, ChatError.Temp>>

    suspend fun closeSocket(): Result<Unit, ChatError.Temp>
}