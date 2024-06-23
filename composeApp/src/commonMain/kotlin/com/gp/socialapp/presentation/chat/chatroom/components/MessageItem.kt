package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.chat.model.Message
import com.gp.socialapp.data.chat.model.MessageAttachment
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.util.getPlatform


expect fun Modifier.rowModifier(topPadding: Dp, onHoverChanged: (Boolean) -> Unit): Modifier

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    message: Message,
    onFileClicked: (MessageAttachment) -> Unit,
    onImageClicked: (String) -> Unit,
    onUserClicked: (String) -> Unit,
    dropDownItems: List<DropDownItem>,
    onDropDownItemClicked: (DropDownItem, Long, String) -> Unit,
    isPrivateChat: Boolean,
    isSameSender: Boolean,
    isCurrentUser: Boolean,
    maxScreenWidthDP: Dp,
    maxScreenHeightDP: Dp,
    windowWidthSizeClass: WindowWidthSizeClass,
) {
    val topPadding = if (isSameSender) 2.dp else 12.dp
    val timestamp = message.createdAt.format("HH:mm a")
    val horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
    val backgroundColor = if (isCurrentUser) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.surfaceVariant
    val surfaceShape = if (isSameSender && isCurrentUser) {
        RoundedCornerShape(8.dp, 4.dp, 8.dp, 8.dp)
    } else if (isCurrentUser) {
        RoundedCornerShape(8.dp, 0.dp, 8.dp, 8.dp)
    } else if (isSameSender) {
        RoundedCornerShape(4.dp, 8.dp, 8.dp, 8.dp)
    } else {
        RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp)
    }
    var isDropDownMenuVisible by rememberSaveable { mutableStateOf(false) }
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    var isHovered by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.rowModifier(topPadding, onHoverChanged = { isHovered = it }),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.Top
    ) {
        MessageUserAvatar(isCurrentUser = isCurrentUser,
            isSameSender = isSameSender,
            isPrivateChat = isPrivateChat,
            imageURL = message.senderPfpUrl,
            onUserClicked = { onUserClicked(message.senderId) })
        if (!isSameSender && !isCurrentUser) {
            Column(
                modifier = Modifier.background(
                    color = backgroundColor,
                    shape = TriangleEdgeShape(
                        if (windowWidthSizeClass == WindowWidthSizeClass.Compact) 15 else 5,
                        false
                    )
                ).align(Alignment.Top),
                content = {}
            )
        }
        if (isCurrentUser && isHovered) {
            IconButton(onClick = {
                isDropDownMenuVisible = true
            },
                modifier = Modifier.onGloballyPositioned {
                    pressOffset = it.localToWindow(Offset.Zero).let {
                        DpOffset(it.x.dp, it.y.dp)
                    }
                }.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = null
                )
            }
        }
        Surface(
            shadowElevation = 4.dp,
            color = backgroundColor,
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
                    MessageUserName(name = message.senderName,
                        onUserClick = { onUserClicked(message.senderId) })
                }
                if (message.content.isBlank() && MimeType.getMimeTypeFromFileName(message.attachment.name) is MimeType.Image) {
                    val imageURL = message.attachment.url
                    ImageMessageWithTimestamp(
                        imageURL = imageURL,
                        onImageClicked = { onImageClicked(imageURL) },
                        maxHeight = maxScreenHeightDP * 0.4f,
                        timestamp = timestamp
                    )
                } else {
                    if (message.hasAttachment) {
                        MessageAttachment(
                            fileUrl = message.attachment.url,
                            fileName = message.attachment.name,
                            fileSize = message.attachment.size,
                            onFileClicked = {
                                onFileClicked(
                                    message.attachment
                                )
                            },
                            onImageClicked = { onImageClicked(message.attachment.url) },
                            maxHeight = maxScreenHeightDP * 0.4f
                        )
                    }
                    MessageTextWithTimestamp(
                        content = message.content, timestamp = timestamp
                    )
                }
            }
        }
        DropdownMenu(
            expanded = isDropDownMenuVisible, onDismissRequest = {
                isDropDownMenuVisible = false
            }, offset = pressOffset.copy(
                y = if (isHovered) pressOffset.y - itemHeight else pressOffset.y - itemHeight
            )
        ) {
            dropDownItems.forEach { item ->
                DropdownMenuItem(text = {
                    Text(text = item.value)
                }, onClick = {
                    isDropDownMenuVisible = false
                    onDropDownItemClicked(item, message.id, message.content)
                })
            }

        }
//        if (!isCurrentUser && isHovered) {
//            IconButton(onClick = {
//                isDropDownMenuVisible = true
//            },
//                modifier = Modifier.onGloballyPositioned {
//                    val position = it.boundsInWindow().topLeft
//                    pressOffset = DpOffset(position.x.dp, position.y.dp)
//                }) {
//                Icon(
//                    imageVector = Icons.Default.MoreHoriz,
//                    contentDescription = null
//                )
//            }
//        }
        if (!isSameSender && isCurrentUser) {
            Column(
                modifier = Modifier.background(
                    color = backgroundColor,
                    shape = TriangleEdgeShape(
                        if (windowWidthSizeClass == WindowWidthSizeClass.Compact) 15 else 5,
                        true
                    )
                ).align(Alignment.Top),
                content = {}
            )
        }
        if (isCurrentUser) {
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

class TriangleEdgeShape(val offset: Int, val endAligned: Boolean) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            if (endAligned) {
                moveTo(x = 0f, y = size.height)
                lineTo(x = 0f + offset, y = size.height)
                lineTo(x = 0f, y = size.height + offset)
            } else {
                moveTo(x = 0f, y = size.height)
                lineTo(x = 0f - offset, y = size.height)
                lineTo(x = 0f, y = size.height + offset)
            }
        }
        return Outline.Generic(path = trianglePath)
    }
}