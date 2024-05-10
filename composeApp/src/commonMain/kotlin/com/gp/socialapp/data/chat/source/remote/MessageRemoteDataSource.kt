package com.gp.socialapp.data.chat.source.remote

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MessageRemoteDataSource {
    suspend fun fetchChatMessages(roomId: Long, scope: CoroutineScope): Flow<Result<List<Message>>>
    suspend fun sendMessage(
        messageContent: String,
        roomId: Long,
        senderId: String,
        senderName: String,
        senderPfpUrl: String,
        attachment: MessageAttachment,
    ): Result<Nothing>

    suspend fun updateMessage(
        messageId: Long, roomId: Long, content: String
    ): Result<Nothing>

    suspend fun deleteMessage(
        messageId: Long, roomId: Long
    ): Result<Nothing>

}