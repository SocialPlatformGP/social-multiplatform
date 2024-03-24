package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.twotone.KeyboardDoubleArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomRow(
    upVotes: List<String>,
    downVotes: List<String>,
    commentCount: Int,
    votes: Int,
    onUpVoteClicked: () -> Unit,
    onDownVoteClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    currentUserID: String,
    filesCount: Int = 0,
    onShowFilesClicked: () -> Unit,
    onShareClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp, end = 8.dp, bottom = 8.dp
            )
            .sizeIn(
                maxHeight = 35.dp
            ),
        horizontalArrangement = Arrangement.Start
    ) {
        OutlinedButton(
            onClick = onUpVoteClicked,
            contentPadding = PaddingValues(6.dp),
            border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline),
        ) {
            Icon(
                imageVector = Icons.TwoTone.KeyboardDoubleArrowUp,
                contentDescription = "UpVote",
                tint = if (upVotes.contains(currentUserID)) Color.Green else MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .clickable {
                        onUpVoteClicked()
                    },
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = votes.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                modifier = Modifier.sizeIn(
                    minWidth = 20.dp
                ),
                textAlign = TextAlign.Center,
                color = when {
                    upVotes.contains(currentUserID) -> Color.Green
                    downVotes.contains(currentUserID) -> Color.Red
                    else -> MaterialTheme.colorScheme.onPrimaryContainer
                },
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Filled.KeyboardDoubleArrowDown,
                contentDescription = "DownVote",
                tint = if (downVotes.contains(currentUserID)) Color.Red else MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .clickable {
                        onDownVoteClicked()
                    }
            )


        }
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedButton(
            onClick = onCommentClicked,
            contentPadding = PaddingValues(
                horizontal = 12.dp,
            ),
            border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Chat,
                    contentDescription = "Comment",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = commentCount.toString(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        if (filesCount > 0) {
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedButton(
                onClick = onShowFilesClicked,
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                ),
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline),

                ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Attachment,
                        contentDescription = "Attachments",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = filesCount.toString(),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            onClick = { onShareClicked() },
            contentPadding = PaddingValues(),
            border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Post",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}