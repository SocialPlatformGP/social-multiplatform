package com.gp.socialapp.presentation.chat.chatroom

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.repository.MessageRepository
import com.gp.socialapp.data.chat.repository.RoomRepository
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ChatRoomScreenModel(
    private val messageRepo: MessageRepository,
    private val roomRepo: RoomRepository,
    private val authRepo: AuthenticationRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(ChatRoomUiState())
    val uiState = _uiState.asStateFlow()
    private var isPrivate by Delegates.notNull<Boolean>()
    private lateinit var roomId: String
    fun initScreen(roomId: String, isPrivate: Boolean) {
            this@ChatRoomScreenModel.isPrivate = isPrivate
            this@ChatRoomScreenModel.roomId = roomId
            getCurrentUserId()
            getMessages()
    }

    private fun getMessages() {
        screenModelScope.launch(DispatcherIO) {
            messageRepo.fetchChatMessages(roomId).collect { result ->
                when (result) {
                    is Result.SuccessWithData -> {
                        _uiState.update { it.copy(messages = result.data) }
                    }

                    is Result.Loading -> {
                        //TODO handle loading
                    }

                    is Result.Error -> {
                        //TODO handle error
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun getCurrentUserId() {
        screenModelScope.launch (DispatcherIO) {
//            val userId = authRepo.getCurrentLocalUserId()
//            _uiState.update { it.copy(currentUserId = userId) }
            _uiState.update { it.copy(currentUserId = "1") }
        }
    }

    private fun sendMessage(content: String) {
        screenModelScope.launch(DispatcherIO) {
            val result = messageRepo.sendMessage(
                messageContent = content,
                roomId = roomId,
                senderId = _uiState.value.currentUserId,
                attachment = _uiState.value.currentAttachment
            )
            if (result is Result.Success) {
                _uiState.update { it.copy(currentAttachment = MessageAttachment()) }
            } else if (result is Result.Error) {
                //TODO handle error
            }
        }
    }

    private fun updateMessage(messageId: String, content: String) {
        screenModelScope.launch(DispatcherIO) {
            val result = messageRepo.updateMessage(messageId, roomId, content)
            if (result is Result.Error) {
                //TODO handle error
            }
        }
    }

    private fun addAttachment(byteArray: ByteArray, fileName: String, fileType: String) {
        screenModelScope.launch(DispatcherIO) {
            _uiState.update {
                it.copy(
                    currentAttachment = _uiState.value.currentAttachment.copy(
                        byteArray = byteArray, name = fileName, type = fileType
                    )
                )
            }
        }
    }

    private fun deleteMessage(messageId: String) {
        screenModelScope.launch(DispatcherIO) {
            val result = messageRepo.deleteMessage(messageId, roomId)
            if (result is Result.Error) {
                //TODO handle error
            }
        }
    }

    fun handleUiAction(action: ChatRoomAction) {
        when (action) {
            is ChatRoomAction.OnSendMessage -> {
                sendMessage(action.message)
            }

            is ChatRoomAction.OnUpdateMessage -> {
                updateMessage(action.messageId, action.message)
            }

            is ChatRoomAction.OnFileClicked -> {
                //TODO implement
            }

            is ChatRoomAction.OnAttachmentPicked -> {
                addAttachment(action.byteArray, action.fileName, action.fileType)
            }

            is ChatRoomAction.OnDeleteMessage -> {
                deleteMessage(action.messageId)
            }

            else -> Unit
        }
    }
}