package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.data.chat.source.remote.model.response.NewDataResponse
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface MessageRemoteDataSource {
    suspend fun connectToSocket(userId: String, roomId: String): Result<Unit,ChatError.Temp>

    fun fetchChatMessages(request: MessageRequest.FetchMessages): Flow<Result<List<Message>,ChatError.Temp>>
    suspend fun sendMessage(request: MessageRequest.SendMessage): Result<Unit,ChatError.Temp>
    suspend fun updateMessage(request: MessageRequest.UpdateMessage): Result<Unit,ChatError.Temp>
    suspend fun observeMessages(): Flow<Result<NewDataResponse,ChatError.Temp>>
    suspend fun deleteMessage(request: MessageRequest.DeleteMessage): Result<Unit,ChatError.Temp>
    suspend fun reportMessage(request: MessageRequest.ReportMessage): Result<Unit,ChatError.Temp>
    suspend fun closeSocket()
}