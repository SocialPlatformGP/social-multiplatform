package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface SocketService {
    suspend fun connectToSocket(userId: String): Result<Nothing>
    fun observeMessages(): Flow<Result<Message>>
    suspend fun sendMessage(message: MessageRequest.SendMessage): Result<Nothing>

    suspend fun closeSocket(): Result<Nothing>
}