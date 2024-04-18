package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.source.local.MessageLocalDataSource
import com.gp.socialapp.data.chat.source.remote.MessageRemoteDataSource
import com.gp.socialapp.data.chat.source.remote.model.request.MessageRequest
import com.gp.socialapp.util.Platform
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.getPlatform
import kotlinx.coroutines.flow.Flow

class MessageRepositoryImpl(
    private val messageRemoteDataSource: MessageRemoteDataSource,
    private val messageLocalDataSource: MessageLocalDataSource
) : MessageRepository {
    override suspend fun connectToSocket(userId: String, roomId: String): Result<Nothing> =
        messageRemoteDataSource.connectToSocket(userId, roomId)

    override suspend fun fetchChatMessages(chatId: String): Flow<Result<List<Message>>> {
        val platform = getPlatform()
        val jsRequest = MessageRequest.FetchMessages(chatId)
        return if (platform == Platform.JS) messageRemoteDataSource.fetchChatMessages(jsRequest)
        else messageLocalDataSource.getMessagesFlow(chatId).also {
            val lastMessage = messageLocalDataSource.getLastLocalMessage(chatId)
            val request = MessageRequest.FetchMessages(
                chatId, timestamp = (lastMessage as? Result.SuccessWithData<Message>)?.data?.createdAt?:0L
            )
            messageRemoteDataSource.fetchChatMessages(request).collect { result ->
                when (result) {
                    is Result.SuccessWithData -> {
                        messageLocalDataSource.insertMessages(*result.data.toTypedArray())
                    }

                    is Result.Error -> {
                        //TODO Handle error
                    }

                    else -> Unit
                }
            }
        }
    }

    override suspend fun closeSocket() {
        messageRemoteDataSource.closeSocket()
    }

    override fun observeMessages(): Flow<Result<Message>> =
        messageRemoteDataSource.observeMessages()

    override suspend fun sendMessage(
        messageContent: String, roomId: String, senderId: String, attachment: MessageAttachment
    ): Result<Nothing> {
        val request = MessageRequest.SendMessage(
            content = messageContent,
            roomId = roomId,
            senderId = senderId,
            hasAttachment = attachment.type.isNotBlank(),
            attachment = attachment
        )
        return messageRemoteDataSource.sendMessage(request)
    }

    override suspend fun updateMessage(
        messageId: String, roomId: String, updatedContent: String
    ): Result<Nothing> {
        val request = MessageRequest.UpdateMessage(
            messageId = messageId, roomId = roomId, updatedContent = updatedContent
        )
        val result = messageRemoteDataSource.updateMessage(request)
        if(getPlatform() == Platform.JS) return result
        return if (result is Result.Success) {
            messageLocalDataSource.updateMessage(
                Message(
                    id = messageId, content = updatedContent, roomId = roomId
                )
            )
        } else {
            Result.Error("An error occurred")
        }
    }

    override suspend fun deleteMessage(messageId: String, chatId: String): Result<Nothing> {
        val request = MessageRequest.DeleteMessage(
            messageId = messageId, roomId = chatId
        )
        val result = messageRemoteDataSource.deleteMessage(request)
        if(getPlatform() == Platform.JS) return result
        return if (result is Result.Success) {
            messageLocalDataSource.deleteMessage(messageId)
        } else {
            Result.Error("An error occurred")
        }
    }
}