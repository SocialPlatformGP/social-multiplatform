package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.source.remote.RecentRoomRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.SocketService

class RecentRoomRepositoryImpl(
    private val remoteDataSource: RecentRoomRemoteDataSource,
    private val socketService: SocketService
) : RecentRoomRepository {
    override fun getAllRecentRooms(userId: String) =
        remoteDataSource.getAllRecentRooms(userId)

    override suspend fun connectToSocket(userId: String) =
        socketService.connectToSocket(userId)

    override fun getMessages(userId: String) = socketService.getMessages(userId)

}