package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.presentation.chat.chatroom.ChatRoomAction
import com.gp.socialapp.util.LocalDateTimeUtil.getDateHeader

@Composable
fun MessagesContent(
    modifier: Modifier = Modifier,
    scrollState: LazyListState,
    messages: List<Message>,
    currentUserId: String,
    isPrivateChat: Boolean,
    onAction: (ChatRoomAction) -> Unit,
    dropDownItems: List<DropDownItem>,
    maxScreenWidthDP: Dp,
    maxScreenHeightDP: Dp,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 2.dp),
        reverseLayout = true,
        state = scrollState,
    ) {
        items(messages.size) { index ->
            val previousMessage = messages.getOrNull(index + 1)
            val message = messages[index]
            val isSameSender = previousMessage?.senderId.equals(message.senderId)
            val isCurrentUser = message.senderId == currentUserId
            MessageItem(
                message = message,
                onFileClicked = { attachment -> onAction(ChatRoomAction.OnFileClicked(attachment)) },
                onImageClicked = { url -> onAction(ChatRoomAction.OnImageClicked(url)) },
                onUserClicked = { id -> onAction(ChatRoomAction.OnUserClicked(id)) },
                isPrivateChat = isPrivateChat,
                isSameSender = isSameSender,
                isCurrentUser = isCurrentUser,
                dropDownItems = dropDownItems,
                onDropDownItemClicked = { item, id, content ->
                    onAction(
                        ChatRoomAction.OnDropDownItemClicked(
                            item, id, content
                        )
                    )
                },
                maxScreenWidthDP = maxScreenWidthDP,
                maxScreenHeightDP = maxScreenHeightDP,
            )
            if (previousMessage?.createdAt?.getDateHeader() != message.createdAt.getDateHeader()) {
                DateHeader(
                    createdAt = message.createdAt,
                )
            }
        }
    }
}