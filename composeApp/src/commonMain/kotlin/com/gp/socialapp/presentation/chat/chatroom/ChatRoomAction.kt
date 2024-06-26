package com.gp.socialapp.presentation.chat.chatroom

import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.presentation.chat.chatroom.components.DropDownItem
import com.mohamedrejeb.calf.picker.FilePickerFileType

sealed interface ChatRoomAction {
    data class OnImageClicked(val imageUrl: String) : ChatRoomAction
    data class OnUserClicked(val userId: String) : ChatRoomAction
    data class OnDropDownItemClicked(
        val action: DropDownItem, val messageId: Long, val messageContent: String
    ) : ChatRoomAction

    data object OnRemoveAttachment : ChatRoomAction

    data class OnSendMessage(val message: String) : ChatRoomAction
    data class OnAttachClicked(val type: FilePickerFileType) : ChatRoomAction
    data class OnUpdateMessage(val messageId: Long, val message: String) : ChatRoomAction
    data class OnFileClicked(val attachment: MessageAttachment) : ChatRoomAction
    data class OnAttachmentPicked(
        val byteArray: ByteArray, val fileName: String, val fileType: String
    ) : ChatRoomAction

    data class OnChatHeaderClicked(val roomId: Long, val isPrivate: Boolean) : ChatRoomAction
    data object OnBackPressed : ChatRoomAction
    data class OnDeleteMessage(val messageId: Long) : ChatRoomAction
    data class OnReportMessage(val messageId: Long) : ChatRoomAction
}