package com.gp.socialapp.presentation.post.postDetails.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.post.source.remote.model.NestedReply
import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.presentation.post.feed.ReplyEvent
import com.gp.socialapp.presentation.post.feed.components.UserImage
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.delete
import socialmultiplatform.composeapp.generated.resources.edit
import socialmultiplatform.composeapp.generated.resources.report
import socialmultiplatform.composeapp.generated.resources.share


@Composable
fun ReplyItem(
    nestedReply: NestedReply,
    currentUserId: String,
    level: Int,
    replyEvent: (ReplyEvent) -> Unit
) {
    val padding = with(LocalDensity.current) { 16.dp.toPx() }
    Column {
        val color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer
        androidx.compose.material3.Card(
            modifier = Modifier
                .drawBehind {
                    repeat(level + 1) {
                        drawLine(
                            color = color.copy(alpha = 1f),
                            start = Offset(it * padding, 0f),
                            end = Offset(it * padding, size.height),
                            strokeWidth = 2f
                        )
                    }
                }
                .padding(start = (16.dp * level) + 8.dp, end = 8.dp),
            shape = ShapeDefaults.Medium,
            border = BorderStroke(1.dp, Color.Gray),
            colors = CardDefaults.cardColors(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.onSecondary,

                )

        ) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserImage(
                        imageLink = nestedReply.reply?.authorImageLink ?: "",
                        size = 26.dp
                    )
                    Text(
                        text = nestedReply.reply?.authorName?.run {
                            if (this.length > 10) this.substring(
                                0,
                                10
                            ) else this
                        } ?: " ",
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                end = 4.dp
                            ),
                        overflow = if ((nestedReply.reply?.authorName?.length
                                ?: 0) > 10
                        ) TextOverflow.Ellipsis else TextOverflow.Clip,
                        fontSize = 12.sp,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = nestedReply.reply?.createdAt.toString(),
                        modifier = Modifier
                            .padding(
                                start = 4.dp,
                                end = 8.dp
                            ),
                        color = Color.Gray,
//                        overflow = if ((nestedReply.reply.createdAt.length ?: 0) > 10) {
//                            TextOverflow.Ellipsis
//                        } else TextOverflow.Clip,
                        fontSize = 12.sp
                    )

                }

                Text(
                    text = nestedReply.reply?.content ?: "",
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 4.dp,
                            top = 4.dp
                        ),
                    fontSize = 14.sp,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 4.dp,
                            end = 4.dp,
                        )
                        .sizeIn(maxHeight = 28.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        var visible by remember { mutableStateOf(false) }
                        IconButton(
                            onClick = {
                                visible = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "More options"
                            )
                        }
                        val dropDownItems = if (nestedReply.reply?.authorID == currentUserId) {
                            listOf(
                                ReplyDropDownItem(stringResource(Res.string.edit)) {
                                    //TODO(Implement Reply Editing)
                                    replyEvent(
                                        ReplyEvent.OnReplyEdited(
                                            reply = nestedReply.reply ?: Reply()
                                        )
                                    )
                                },
                                ReplyDropDownItem(stringResource(Res.string.delete)) {
                                    replyEvent(
                                        ReplyEvent.OnReplyDeleted(
                                            reply = nestedReply.reply ?: Reply()
                                        )
                                    )
                                },
                                ReplyDropDownItem(stringResource(Res.string.share)) {
                                    replyEvent(
                                        ReplyEvent.OnShareReply(
                                            reply = nestedReply.reply ?: Reply()
                                        )
                                    )
                                }
                            )
                        } else {
                            listOf(
                                ReplyDropDownItem(stringResource(Res.string.share)) {
                                    replyEvent(
                                        ReplyEvent.OnShareReply(
                                            reply = nestedReply.reply ?: Reply()
                                        )
                                    )
                                },
                                ReplyDropDownItem(stringResource(Res.string.report)) {
                                    replyEvent(
                                        ReplyEvent.OnReportReply(
                                            reply = nestedReply.reply ?: Reply()
                                        )
                                    )
                                }
                            )
                        }
                        DropdownMenu(
                            expanded = visible,
                            onDismissRequest = { visible = false },
                        ) {
                            dropDownItems.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item.text) },
                                    onClick = {
                                        item.onClick()
                                        visible = false
                                    })
                            }
                        }
                    }
                    IconButton(
                        onClick = {
                            replyEvent(ReplyEvent.OnAddReply(reply = nestedReply.reply ?: Reply()))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Comment,
                            contentDescription = "Add a comment"
                        )
                    }
                    IconButton(
                        onClick = {
                            replyEvent(
                                ReplyEvent.OnReplyUpVoted(
                                    reply = nestedReply.reply ?: Reply()
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ThumbUp,
                            contentDescription = "Like"
                        )
                    }
                    Text(text = (nestedReply.reply?.votes?:0).toString())
                    IconButton(
                        onClick = {
                            replyEvent(
                                ReplyEvent.OnReplyDownVoted(
                                    reply = nestedReply.reply ?: Reply()
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ThumbDown,
                            contentDescription = "Share"
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

data class ReplyDropDownItem(
    val text: String,
    val onClick: () -> Unit
)