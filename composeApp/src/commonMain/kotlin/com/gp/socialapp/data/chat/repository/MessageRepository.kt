package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.source.remote.model.response.NewDataResponse
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun connectToSocket(userId: String, roomId: String): Result<Nothing>

    suspend fun fetchChatMessages(chatId: String): Flow<Result<List<Message>>>

    suspend fun closeSocket()

    suspend fun observeMessages(): Flow<Result<NewDataResponse>>
    suspend fun sendMessage(
        messageContent: String,
        roomId: String,
        senderId: String,
        attachment: MessageAttachment
    ): Result<Nothing>

    suspend fun updateMessage(
        messageId: String,
        roomId: String,
        updatedContent: String
    ): Result<Nothing>

    suspend fun deleteMessage(messageId: String, chatId: String): Result<Nothing>

    suspend fun openAttachment(url: String, mimeType: String)
}