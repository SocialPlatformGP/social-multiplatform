package com.gp.socialapp.presentation.chat.chatroom

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.repository.MessageRepository
import com.gp.socialapp.data.chat.repository.RoomRepository
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        connectToSocket()
        getMessages()
    }

    private fun getMessages() {
        screenModelScope.launch(DispatcherIO) {
            messageRepo.fetchChatMessages(roomId).collect { result ->
                result.onSuccessWithData { messages ->
                    _uiState.update { it.copy(messages = messages + it.messages) }
                }.onFailure {
                    println("Error: $it")
                }
            }
        }
    }

    private fun observeMessages() {
        screenModelScope.launch(DispatcherIO) {
            messageRepo.observeMessages().onEach { message ->
                if (message is Result.SuccessWithData) {
                    val newList = _uiState.value.messages.toMutableList()
                    newList.add(0, message.data)
                    _uiState.update {
                        it.copy(messages = newList)
                    }
                }
            }.launchIn(screenModelScope)
        }
    }

    private fun getCurrentUserId() {
        screenModelScope.launch(DispatcherIO) {
            val userId = authRepo.getCurrentLocalUserId()
            _uiState.update { it.copy(currentUserId = userId) }
        }
    }

    private fun sendMessage(content: String) {
        screenModelScope.launch(DispatcherIO) {
            messageRepo.sendMessage(
                messageContent = content,
                roomId = roomId,
                senderId = _uiState.value.currentUserId,
                attachment = _uiState.value.currentAttachment
            ).onSuccess {
                println("message sent $content $roomId ${_uiState.value.currentUserId}")
                _uiState.update { it.copy(currentAttachment = MessageAttachment()) }
            }.onFailure {
                println("message not sent")
            }
        }
    }

    private fun connectToSocket() {
        screenModelScope.launch(DispatcherIO) {
            messageRepo.connectToSocket(
                uiState.value.currentUserId,
                roomId
            ).onSuccess {
                println("Socket connected")
                observeMessages()
            }.onFailure {
                println("Socket connection failed")
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

    fun onClear() {
        screenModelScope.launch(DispatcherIO) {
            messageRepo.closeSocket()
            _uiState.update { ChatRoomUiState() }
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

            is ChatRoomAction.OnRemoveAttachment -> {
                _uiState.update { it.copy(currentAttachment = MessageAttachment()) }
            }

            else -> Unit
        }
    }
}