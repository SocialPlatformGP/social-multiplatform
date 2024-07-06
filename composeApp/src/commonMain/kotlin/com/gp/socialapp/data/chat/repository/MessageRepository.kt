package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun fetchChatMessages(roomId: Long): Flow<Result<List<Message>,ChatError>>
    suspend fun sendMessage(
        messageContent: String,
        roomId: Long,
        senderId: String,
        senderName: String,
        senderPfpUrl: String,
        attachment: MessageAttachment,
    ): Result<Unit,ChatError>

    suspend fun updateMessage(
        messageId: Long, roomId: Long, content: String
    ): Result<Unit,ChatError>

    suspend fun deleteMessage(
        messageId: Long, roomId: Long
    ): Result<Unit,ChatError>

    suspend fun openAttachment(path: String, name: String, mimeType: String)
    suspend fun reportMessage(
        messageId: Long,
        roomId: Long,
        reporterId: String
    ): Result<Unit, ChatError>
}