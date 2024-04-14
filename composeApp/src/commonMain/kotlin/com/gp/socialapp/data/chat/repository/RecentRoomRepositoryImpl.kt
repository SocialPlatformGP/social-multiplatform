package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.source.remote.RecentRoomRemoteDataSource

class RecentRoomRepositoryImpl(
    private val remoteDataSource: RecentRoomRemoteDataSource,
) : RecentRoomRepository {
    override suspend fun getAllRecentRooms(userId: String) =
        remoteDataSource.getAllRecentRooms(userId)

}