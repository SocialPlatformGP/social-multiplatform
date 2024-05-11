package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface RecentRoomRemoteDataSource {
    fun fetchRecentRooms(userId: String): Flow<Result<List<RecentRoom>>>
}