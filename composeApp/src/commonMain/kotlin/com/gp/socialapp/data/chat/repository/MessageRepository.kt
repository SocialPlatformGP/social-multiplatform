package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.source.remote.model.response.NewDataResponse
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun connectToSocket(userId: String, roomId: String): Result<Unit,ChatError.Temp>

    suspend fun fetchChatMessages(chatId: String): Flow<Result<List<Message>, ChatError.Temp>>

    suspend fun closeSocket()

    suspend fun observeMessages(): Flow<Result<NewDataResponse, ChatError.Temp>>
    suspend fun sendMessage(
        messageContent: String,
        roomId: String,
        senderId: String,
        attachment: MessageAttachment
    ): Result<Unit, ChatError.Temp>
    suspend fun reportMessage(
        messageId: String,
        roomId: String,
        reporterId: String
    ): Result<Unit, ChatError.Temp>
    suspend fun updateMessage(
        messageId: String,
        roomId: String,
        updatedContent: String
    ): Result<Unit, ChatError.Temp>

    suspend fun deleteMessage(messageId: String, chatId: String): Result<Unit, ChatError.Temp>

    suspend fun openAttachment(url: String, mimeType: String)
}