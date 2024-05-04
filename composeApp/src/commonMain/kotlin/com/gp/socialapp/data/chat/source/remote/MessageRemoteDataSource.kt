package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.data.chat.source.remote.model.response.NewDataResponse
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface MessageRemoteDataSource {
    suspend fun connectToSocket(userId: String, roomId: String): Result<Nothing>

    fun fetchChatMessages(request: MessageRequest.FetchMessages): Flow<Result<List<Message>>>
    suspend fun sendMessage(request: MessageRequest.SendMessage): Result<Nothing>
    suspend fun updateMessage(request: MessageRequest.UpdateMessage): Result<Nothing>
    suspend fun observeMessages(): Flow<Result<NewDataResponse>>
    suspend fun deleteMessage(request: MessageRequest.DeleteMessage): Result<Nothing>
    suspend fun reportMessage(request: MessageRequest.ReportMessage): Result<Nothing>
    suspend fun closeSocket()
}