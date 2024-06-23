package com.gp.socialapp.data.chat.source.local

import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.source.local.model.MessageEntity
import com.gp.socialapp.data.chat.source.local.model.MessageEntity.Companion.toEntity
import com.gp.socialapp.util.ChatError
import com.gp.socialapp.util.Result
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.UpdatedResults
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MessageLocalDataSourceImpl(
    private val realm: Realm
) : MessageLocalDataSource {
    override fun getMessagesFlow(roomId: String): Flow<Result<List<Message>,ChatError>> =
        flow {
            try {
                val messagesFlow = realm.query(
                    clazz = MessageEntity::class,
                    query = "roomId == $0",
                    roomId
                ).sort(
                    property = "createdAt",
                    sortOrder = Sort.DESCENDING
                ).find().asFlow()
                messagesFlow.collect { results ->
                    when (results) {
                        is InitialResults<MessageEntity> -> {
                            val data = results.list.map { it.toMessage() }
                            emit(Result.Success(data))
                        }

                        is UpdatedResults -> {
                            val data = results.list.map { it.toMessage() }
                            emit(Result.Success(data))
                        }
                    }
                }
            } catch (e: Exception) {
                emit(Result.Error(ChatError.SERVER_ERROR))
            }
        }

    override suspend fun insertMessages(vararg messages: Message): Result<Unit,ChatError> {
        return try {
            realm.write {
                messages.forEach { message ->
                    val messageEntity = message.toEntity()
                    copyToRealm(
                        instance = messageEntity,
                        updatePolicy = UpdatePolicy.ALL
                    )
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(ChatError.SERVER_ERROR)
        }
    }

    override suspend fun deleteMessage(messageId: String): Result<Unit,ChatError> {
        return try {
            realm.write {
                val messageToDelete: MessageEntity =
                    query(clazz = MessageEntity::class, "id == $0", messageId).find().first()
                delete(messageToDelete)

            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(ChatError.SERVER_ERROR)
        }
    }

    override suspend fun updateMessage(message: Message): Result<Unit,ChatError> {
        return try {
            realm.write {
                val messageToUpdate =
                    query(clazz = MessageEntity::class, "id == $0", message.id).find().first()
                if (message.content.isNotBlank()) messageToUpdate.content = message.content
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(ChatError.SERVER_ERROR)
        }
    }

    override suspend fun getLastLocalMessage(chatId: String): Result<Message,ChatError> {
        return try {
            val messageEntity = realm.query(
                clazz = MessageEntity::class,
                query = "roomId == $0",
                chatId
            ).sort(
                property = "createdAt",
                sortOrder = Sort.DESCENDING
            ).find().first()
            Result.Success(messageEntity.toMessage())
        } catch(e: Exception) {
            Result.Error(ChatError.SERVER_ERROR)
        }
    }
}
