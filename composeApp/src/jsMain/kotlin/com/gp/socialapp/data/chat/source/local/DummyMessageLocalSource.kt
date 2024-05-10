package com.gp.socialapp.data.chat.source.local

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Result.Companion.failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object DummyMessageLocalSource: MessageLocalDataSource {
    override fun getMessagesFlow(roomId: String): Flow<Result<List<Message>,ChatError.Temp>> = flow { }

    override suspend fun insertMessages(vararg messages: Message): Result<Unit,ChatError.Temp> = Result.success(Unit)

    override suspend fun deleteMessage(messageId: String): Result<Unit,ChatError.Temp> = Result.success(Unit)

    override suspend fun updateMessage(message: Message): Result<Unit,ChatError.Temp> = Result.success(Unit)
    override suspend fun getLastLocalMessage(chatId: String): Result<Message, ChatError.Temp> {
        return failure(ChatError.Temp.SERVER_ERROR)
    }
}