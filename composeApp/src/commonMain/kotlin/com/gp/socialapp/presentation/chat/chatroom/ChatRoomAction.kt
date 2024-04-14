package com.gp.socialapp.presentation.chat.chatroom

import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.presentation.chat.chatroom.components.DropDownItem
import com.mohamedrejeb.calf.picker.FilePickerFileType

sealed class ChatRoomAction {
    data class OnImageClicked(val imageUrl: String) : ChatRoomAction()
    data class OnUserClicked(val userId: String) : ChatRoomAction()
    data class OnDropDownItemClicked(
        val action: DropDownItem,
        val messageId: String,
        val messageContent: String
    ) : ChatRoomAction()

    data class OnSendMessage(val message: String) : ChatRoomAction()
    data class OnAttachClicked(val type: FilePickerFileType) : ChatRoomAction()
    data class OnUpdateMessage(val messageId: String, val message: String) : ChatRoomAction()
    data class OnFileClicked(val attachment: MessageAttachment) : ChatRoomAction()
    data class OnAttachmentPicked(val byteArray: ByteArray, val fileName: String, val fileType: String) : ChatRoomAction()
    data class OnChatHeaderClicked(val roomId: String, val isPrivate: Boolean) : ChatRoomAction()
    data object OnBackPressed : ChatRoomAction()
    data class OnDeleteMessage(val messageId: String) : ChatRoomAction()
}