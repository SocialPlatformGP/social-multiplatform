package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.RecentRoomResponse
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface RecentRoomRemoteDataSource {
    suspend fun getAllRecentRooms(userId: String): Flow<Result<List<RecentRoomResponse>>>
}