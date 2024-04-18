package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.source.remote.RecentRoomRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.SocketService
import com.gp.socialapp.util.Result

class RecentRoomRepositoryImpl(
    private val remoteDataSource: RecentRoomRemoteDataSource,
    private val socketService: SocketService
) : RecentRoomRepository {
    override fun getAllRecentRooms(userId: String) =
        remoteDataSource.getAllRecentRooms(userId)

    override suspend fun connectToSocket(currentUserId: String): Result<Nothing> {
        return socketService.connectToSocket(currentUserId)
    }

    override suspend fun closeSocket(): Result<Nothing> {
        return socketService.closeSocket()
    }


}