package com.gp.socialapp.presentation.chat.chatroom

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.data.chat.repository.MessageRepository
import com.gp.socialapp.data.chat.repository.RoomRepository
import com.gp.socialapp.presentation.material.utils.MimeType.Companion.getExtensionFromMimeType
import com.gp.socialapp.presentation.material.utils.MimeType.Companion.getMimeTypeFromFileName
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ChatRoomScreenModel(
    private val messageRepo: MessageRepository,
    private val authRepo: AuthenticationRepository,
    private val roomRepo: RoomRepository,
) : ScreenModel {
    private val _uiState = MutableStateFlow(ChatRoomUiState())
    val uiState = _uiState.asStateFlow()
    private var isPrivate by Delegates.notNull<Boolean>()
    fun initScreen(roomId: Long, isPrivate: Boolean) {
        this@ChatRoomScreenModel.isPrivate = isPrivate
        _uiState.update { oldState -> oldState.copy(isMessagesLoading = true)}
        getChatRoom(roomId)
        fetchChatMessages(roomId)
        getSignedInUser()
    }

    private fun getChatRoom(roomId: Long) {
        screenModelScope.launch (Dispatchers.IO){
            roomRepo.getRoom(roomId).onSuccessWithData { room ->
                _uiState.update { oldState -> oldState.copy(currentRoom = room) }
            }.onFailure {
                println("error getting room")
            }
        }
    }

    private fun getSignedInUser() {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let { result ->
                result.onSuccessWithData { user ->
                    updateCurrentUser(user)
                }
            }
        }
    }

    private fun fetchChatMessages(roomId: Long) {
        screenModelScope.launch(DispatcherIO) {
            messageRepo.fetchChatMessages(roomId).collect { result ->
                result.onSuccessWithData { data ->
                    println("received data in screen model :$data")
                    _uiState.update {
                        it.copy(messages = data, isMessagesLoading = false)
                    }
                }.onFailure {

                }.onLoading {

                }
            }
        }
    }


    private fun updateCurrentUser(user: User) {
        _uiState.update { it.copy(currentUser = user) }
    }

    private fun sendMessage(content: String) {
        screenModelScope.launch(DispatcherIO) {
            if (content.isEmpty() && _uiState.value.currentAttachment.type.isBlank()) return@launch
            _uiState.update{oldState -> oldState.copy(isSendLoading = true)}
            messageRepo.sendMessage(
                messageContent = content,
                roomId = uiState.value.currentRoom.id,
                senderId = _uiState.value.currentUser.id,
                senderName = _uiState.value.currentUser.name,
                senderPfpUrl = _uiState.value.currentUser.profilePictureURL,
                attachment = _uiState.value.currentAttachment
            ).let { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update { it.copy(currentAttachment = MessageAttachment(), isSendLoading = false) }
                    }

                    is Result.Error -> {
                        println("message not sent: ${result.message}")
                        //TODO handle error
                    }

                    else -> Unit
                }
            }
        }
    }


    private fun updateMessage(messageId: Long, content: String) {
        screenModelScope.launch(DispatcherIO) {
            messageRepo.updateMessage(messageId, uiState.value.currentRoom.id, content).onSuccess {
                println("message updated")
            }.onFailure {
                println("message not updated")
            }
        }
    }

    private fun addAttachment(byteArray: ByteArray, fileName: String, fileType: String) {
        screenModelScope.launch(DispatcherIO) {
            _uiState.update {
                it.copy(
                    currentAttachment = _uiState.value.currentAttachment.copy(
                        byteArray = byteArray, name = fileName, type = fileType, size = byteArray.size.toLong()
                    )
                )
            }
        }
    }

    private fun deleteMessage(messageId: Long) {
        screenModelScope.launch(DispatcherIO) {
            val result = messageRepo.deleteMessage(messageId, uiState.value.currentRoom.id)
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

    private fun reportMessage(messageId: Long) {
        screenModelScope.launch(DispatcherIO) {
            messageRepo.reportMessage(messageId, uiState.value.currentRoom.id, _uiState.value.currentUser.id).let{
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
            messageRepo.openAttachment(attachment.path, attachment.name, extension)
        }
    }

    override fun onDispose() {
        screenModelScope.launch {
            _uiState.value = ChatRoomUiState()
        }
        super.onDispose()
    }
}