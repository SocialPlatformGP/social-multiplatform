package com.gp.socialapp.presentation.chat.chatroom

import cafe.adriel.voyager.core.model.ScreenModel
import com.gp.socialapp.data.chat.repository.MessageRepository
import com.gp.socialapp.data.chat.repository.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatRoomScreenModel(
    private val messageRepo: MessageRepository,
    private val roomRepo: RoomRepository,
) : ScreenModel {
    private val _uiState = MutableStateFlow(ChatRoomUiState())
    val uiState = _uiState.asStateFlow()
    fun initScreen(roomId: String, isPrivate: Boolean) {
        //TODO implement
    }
    private fun sendMessage(content: String){
        //TODO implement
    }
    private fun updateMessage(messageId: String, content: String){
        //TODO implement
    }
    private fun addAttachment(byteArray: ByteArray, fileName: String, fileType: String){
        //TODO implement
    }
    private fun deleteMessage(messageId: String){
        //TODO implement
    }

    fun handleUiAction(action: ChatRoomAction) {
        when(action) {
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