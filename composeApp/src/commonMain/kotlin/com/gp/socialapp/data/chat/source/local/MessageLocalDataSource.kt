package com.gp.socialapp.data.chat.source.local

import com.gp.socialapp.data.chat.model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import com.gp.socialapp.util.Result
interface MessageLocalDataSource {
    fun getMessagesFlow(roomId: String): Flow<Result<List<Message>>>
    suspend fun insertMessages(vararg messages: Message): Result<Nothing>
    suspend fun deleteMessage(messageId: String): Result<Nothing>
    suspend fun updateMessage(message: Message): Result<Nothing>
    suspend fun getLastLocalMessage(chatId: String): Result<Message>
}