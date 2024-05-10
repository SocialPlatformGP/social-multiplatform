package com.gp.socialapp.data.chat.source.local

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.util.ChatError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import com.gp.socialapp.util.Result
interface MessageLocalDataSource {
    fun getMessagesFlow(roomId: String): Flow<Result<List<Message>,ChatError.Temp>>
    suspend fun insertMessages(vararg messages: Message): Result<Unit,ChatError.Temp>
    suspend fun deleteMessage(messageId: String): Result<Unit,ChatError.Temp>
    suspend fun updateMessage(message: Message): Result<Unit,ChatError.Temp>
    suspend fun getLastLocalMessage(chatId: String): Result<Message,ChatError>
}