package com.gp.socialapp.data.chat.repository

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.source.remote.MessageRemoteDataSource
import com.gp.socialapp.data.material.source.remote.MaterialRemoteDataSource
import com.gp.socialapp.data.material.utils.FileManager
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class MessageRepositoryImpl(
    private val messageRemoteDataSource: MessageRemoteDataSource,
    private val materialRemoteDataSource: MaterialRemoteDataSource,
    private val fileManager: FileManager
) : MessageRepository {
    override suspend fun fetchChatMessages(
        roomId: Long
    ): Flow<Result<List<Message>, ChatError>> {
        return messageRemoteDataSource.fetchChatMessages(roomId)
    }

    override suspend fun sendMessage(
        messageContent: String,
        roomId: Long,
        senderId: String,
        senderName: String,
        senderPfpUrl: String,
        attachment: MessageAttachment
    ): Result<Unit, ChatError> {
        return messageRemoteDataSource.sendMessage(
            messageContent, roomId, senderId, senderName, senderPfpUrl, attachment
        )
    }

    override suspend fun updateMessage(
        messageId: Long, roomId: Long, content: String
    ): Result<Unit, ChatError> {
        return messageRemoteDataSource.updateMessage(messageId, roomId, content)
    }

    override suspend fun deleteMessage(messageId: Long, roomId: Long): Result<Unit, ChatError> {
        return messageRemoteDataSource.deleteMessage(messageId, roomId)
    }

    override suspend fun openAttachment(path: String, name: String, mimeType: String) {
        try {
            val result = materialRemoteDataSource.downloadChatAttachment(path)
            when (result) {
                is Result.Error -> {
                    println(result.message)
                }

                Result.Loading -> {
                    //TODO
                }

                is Result.Success -> {
                    val localPath = fileManager.saveFile(result.data.data, name, mimeType)
                    fileManager.openFile(localPath, mimeType)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun reportMessage(
        messageId: Long,
        roomId: Long,
        reporterId: String
    ): Result<Unit, ChatError> {
        return messageRemoteDataSource.reportMessage(messageId, roomId, reporterId)
    }


}