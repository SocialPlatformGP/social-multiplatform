package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.RecentRoomResponse
import com.gp.socialapp.data.chat.source.remote.SocketMessage
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface RecentRoomRepository {
    fun getAllRecentRooms(userId: String): Flow<Result<List<RecentRoomResponse>>>
    suspend fun connectToSocket(userId: String): Result<Nothing>
    fun getMessages(userId: String): Flow<SocketMessage>

}