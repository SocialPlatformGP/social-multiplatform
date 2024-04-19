package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.source.remote.RecentRoomRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.SocketService
import com.gp.socialapp.data.chat.source.remote.model.response.NewDataResponse
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class RecentRoomRepositoryImpl(
    private val remoteDataSource: RecentRoomRemoteDataSource,
    private val socketService: SocketService
) : RecentRoomRepository {
    override fun getAllRecentRooms(userId: String) =
        remoteDataSource.getAllRecentRooms(userId)

    override suspend fun connectToSocket(currentUserId: String): Result<Nothing> {
        return socketService.connectToSocket(currentUserId)
    }

    override suspend fun observeNewData(): Flow<Result<NewDataResponse>> {
        println("im in recent room repo")
        return socketService.observeNewData()
    }


    override suspend fun closeSocket(): Result<Nothing> {
        return socketService.closeSocket()
    }


}