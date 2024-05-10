package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.data.chat.source.remote.RecentRoomRemoteDataSource
import com.gp.socialapp.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class RecentRoomRepositoryImpl(
    private val remoteDataSource: RecentRoomRemoteDataSource,
) : RecentRoomRepository {
    override fun fetchRecentRooms(
        userId: String, scope: CoroutineScope
    ): Flow<Result<List<RecentRoom>>> {
        return remoteDataSource.fetchRecentRooms(userId, scope)
    }
}