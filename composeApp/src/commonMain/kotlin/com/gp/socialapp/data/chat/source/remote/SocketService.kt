package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.data.chat.source.remote.model.response.NewDataResponse
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface SocketService {
    suspend fun connectToSocket(userId: String): Result<Unit,ChatError.Temp>
    suspend fun observeNewData(): Flow<Result<NewDataResponse, ChatError.Temp>>
    suspend fun sendMessage(message: MessageRequest.SendMessage): Result<Unit, ChatError.Temp>

    suspend fun closeSocket(): Result<Unit, ChatError.Temp>
    suspend fun observeNewDataMessage(): Flow<Result<NewDataResponse, ChatError.Temp>>

    //    fun observeRecentRooms(): Flow<Result<List<RecentRoomResponse>>>
//    suspend fun connectToRecentSocket(userId: String): Result<Nothing>
}