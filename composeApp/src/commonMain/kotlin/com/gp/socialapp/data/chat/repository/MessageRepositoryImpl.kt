package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.source.local.MessageLocalDataSource
import com.gp.socialapp.data.chat.source.remote.MessageRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class MessageRepositoryImpl(
    private val messageRemoteDataSource: MessageRemoteDataSource,
    private val messageLocalDataSource: MessageLocalDataSource
) : MessageRepository {
    override suspend fun connectToSocket(userId: String, roomId: String): Result<Nothing> =
        messageRemoteDataSource.connectToSocket(userId, roomId)

    override fun fetchChatMessages(chatId: String): Flow<Result<List<Message>>> {
        val request = MessageRequest.FetchMessages(chatId)
        return messageRemoteDataSource.fetchChatMessages(request)
    }

    override fun observeMessages(): Flow<Result<Message>> =
        messageRemoteDataSource.observeMessages()

    override suspend fun sendMessage(
        messageContent: String,
        roomId: String,
        senderId: String,
        attachment: MessageAttachment
    ): Result<Nothing> {
        val request = MessageRequest.SendMessage(
            content = messageContent,
            roomId = roomId,
            senderId = senderId,
            hasAttachment = attachment.type.isBlank(),
            attachment = attachment
        )
        return messageRemoteDataSource.sendMessage(request)
    }

    override suspend fun updateMessage(
        messageId: String,
        roomId: String,
        updatedContent: String
    ): Result<Nothing> {
        val request = MessageRequest.UpdateMessage(
            messageId = messageId,
            roomId = roomId,
            updatedContent = updatedContent
        )
        return messageRemoteDataSource.updateMessage(request)
    }

    override suspend fun deleteMessage(messageId: String, chatId: String): Result<Nothing> {
        val request = MessageRequest.DeleteMessage(
            messageId = messageId,
            roomId = chatId
        )
        return messageRemoteDataSource.deleteMessage(request)
    }
}