package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.chat.model.Message

@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    message: Message,
    onFileClicked: (String, String, String) -> Unit,
    onImageClicked: (String) -> Unit,
    onUserClicked: (String) -> Unit,
    dropDownItems: List<String>,
    onDropDownItemClicked: (String, String, String) -> Unit,
    isPrivateChat: Boolean,
    isSameSender: Boolean,
    isCurrentUser: Boolean,
    maxScreenWidthDP: Dp,
    maxScreenHeightDP: Dp,
) {
    val topPadding = if (isSameSender) 2.dp else 12.dp
//    val extendedFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
//    val shortFormat = DateTimeFormatter.ofPattern("HH:mm")
//    val dateTime = LocalDateTime.parse(message.timestamp, extendedFormat)
//    val timestamp = shortFormat.format(dateTime)
    val dateTime = ""//TODO
    val timestamp = "" //TODO
    val horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
    val backgroundColor = if (isCurrentUser) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.surfaceVariant
    val surfaceShape = if (isCurrentUser && isSameSender) {
        RoundedCornerShape(13.dp, 13.dp, 13.dp, 13.dp)
    } else if (isCurrentUser && !isSameSender) {
        RoundedCornerShape(13.dp, 2.dp, 13.dp, 13.dp)
    } else if (!isCurrentUser && isSameSender) {
        RoundedCornerShape(13.dp, 13.dp, 13.dp, 13.dp)
    } else {
        RoundedCornerShape(2.dp, 13.dp, 13.dp, 13.dp)
    }
    var isDropDownMenuVisible by rememberSaveable { mutableStateOf(false) }
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Row(
        modifier = modifier.padding(
            top = topPadding
        ).fillMaxWidth(), horizontalArrangement = horizontalArrangement
    ) {
        MessageUserAvatar(isCurrentUser = isCurrentUser,
            isSameSender = isSameSender,
            isPrivateChat = isPrivateChat,
            imageURL = message.senderPfpURL,
            onUserClicked = { onUserClicked(message.senderId) })
        Surface(color = backgroundColor,
            shape = surfaceShape,
            modifier = modifier.widthIn(max = maxScreenWidthDP * 0.67f).pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    if (isCurrentUser) {
                        isDropDownMenuVisible = true
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                    }
                })
            }) {
            Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp).onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }) {
                if (!isCurrentUser && !isSameSender && !isPrivateChat) {
                    MessageUserName(
                        name = message.senderName,
                        onUserClick = { onUserClicked(message.senderId) })
                }
//                if (message.fileType.contains("image") && message.message.isBlank()) {
//                    val imageURL = message.fileURI.toString()
//                    ImageMessageWithTimestamp(
//                        imageURL = imageURL,
//                        onImageClicked = { onImageClicked(imageURL) },
//                        maxHeight = maxScreenHeightDP * 0.4f,
//                        timestamp = timestamp
//                    )
//                } else {
//                    if (message.fileURI.toString().isNotBlank()) {
//                        MessageAttachment(
//                            fileURI = message.fileURI,
//                            fileType = message.fileType,
//                            fileName = message.fileNames,
//                            onFileClicked = {
//                                onFileClicked(
//                                    message.fileURI.toString(),
//                                    message.fileType,
//                                    message.fileNames
//                                )
//                            },
//                            onImageClicked = { onImageClicked(message.fileURI.toString()) },
//                            maxHeight = maxScreenHeightDP * 0.4f
//                        )
//                    }
                MessageTextWithTimestamp(
                    content = message.content, timestamp = timestamp
                )
            }
            DropdownMenu(
                expanded = isDropDownMenuVisible, onDismissRequest = {
                    isDropDownMenuVisible = false
                }, offset = pressOffset.copy(
                    y = pressOffset.y - itemHeight
                )
            ) {
                dropDownItems.forEach { item ->
                    DropdownMenuItem(text = {
                        Text(text = item)
                    }, onClick = {
                        isDropDownMenuVisible = false
                        onDropDownItemClicked(item, message.id, message.content)
                    })
                }

            }
        }
    }
    if (isCurrentUser) {
        Spacer(modifier = Modifier.width(8.dp))
    }
}