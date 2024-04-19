package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.data.chat.source.remote.model.response.NewDataResponse
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface SocketService {
    suspend fun connectToSocket(userId: String): Result<Nothing>
    suspend fun observeNewData(): Flow<Result<NewDataResponse>>
    suspend fun sendMessage(message: MessageRequest.SendMessage): Result<Nothing>

    suspend fun closeSocket(): Result<Nothing>
    suspend fun observeNewDataMessage(): Flow<Result<NewDataResponse>>

    //    fun observeRecentRooms(): Flow<Result<List<RecentRoomResponse>>>
//    suspend fun connectToRecentSocket(userId: String): Result<Nothing>
}