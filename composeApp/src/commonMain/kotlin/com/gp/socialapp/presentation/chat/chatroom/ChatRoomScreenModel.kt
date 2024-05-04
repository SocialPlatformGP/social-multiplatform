package com.gp.socialapp.presentation.chat.chatroom

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.repository.MessageRepository
import com.gp.socialapp.data.chat.repository.RoomRepository

import com.gp.socialapp.presentation.material.utils.MimeType.Companion.getExtensionFromMimeType
import com.gp.socialapp.presentation.material.utils.MimeType.Companion.getMimeTypeFromFileName
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Job
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
    private var job: Job? = null
    fun initScreen(roomId: String, isPrivate: Boolean) {
        this@ChatRoomScreenModel.isPrivate = isPrivate
        this@ChatRoomScreenModel.roomId = roomId
        resetState()
        getCurrentUserId()
        getMessages()
    }

    private fun resetState() {
        _uiState.update { ChatRoomUiState() }
    }

    private fun getMessages() {
        screenModelScope.launch(DispatcherIO) {
            messageRepo.fetchChatMessages(roomId).collect { result ->
                result.onSuccessWithData { messages ->
                    _uiState.update { it.copy(messages = messages) }
                }.onFailure {
                    println("Error: $it")
                }
                observeMessages()
            }
        }
    }

    private fun observeMessages() {
        if (job == null) {
            job = screenModelScope.launch(DispatcherIO) {
                messageRepo.observeMessages().collect { result ->
                    result.onSuccessWithData { newData ->
                        println("im in room vm :")
                        if (newData.messages == null || newData.messages.roomId != roomId)
                        else {
                            val newList = _uiState.value.messages.toMutableList()
                            newList.add(0, newData.messages)
                            _uiState.update {
                                it.copy(messages = newList)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getCurrentUserId() {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let {
                when (it) {
                    is Result.SuccessWithData -> {
                        updateCurrentUserId(it.data.id)
                    }

                    is Result.Error -> {
                        //TODO handle error
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun updateCurrentUserId(id: String) {
        _uiState.update { it.copy(currentUserId = id) }
    }

    private fun sendMessage(content: String) {
        screenModelScope.launch(DispatcherIO) {
            if (content.isEmpty() && _uiState.value.currentAttachment.type.isBlank()) return@launch
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
                openAttachment(action.attachment)
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

            is ChatRoomAction.OnReportMessage -> {
                reportMessage(action.messageId)
            }
            else -> Unit
        }
    }

    private fun reportMessage(messageId: String) {
        screenModelScope.launch(DispatcherIO) {
            messageRepo.reportMessage(messageId, roomId, _uiState.value.currentUserId).let{
                when (it) {
                    is Result.Success -> {
                        println("message reported")
                    }

                    is Result.Error -> {
                        println("message not reported")
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun openAttachment(attachment: MessageAttachment) {
        screenModelScope.launch(DispatcherIO) {
            val mimeType = getMimeTypeFromFileName(attachment.name)
            val extension = getExtensionFromMimeType(mimeType)
            messageRepo.openAttachment(attachment.url, extension)
        }
    }
}