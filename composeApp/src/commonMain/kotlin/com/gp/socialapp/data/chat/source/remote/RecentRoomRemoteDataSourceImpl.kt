package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.RecentRoomResponse
import kotlinx.coroutines.flow.Flow

class RecentRoomRemoteDataSourceImpl : RecentRoomRemoteDataSource {
    override suspend fun getAllRecentRooms(userId: String): Flow<Result<List<RecentRoomResponse>>> {
        return TODO()
    }
}