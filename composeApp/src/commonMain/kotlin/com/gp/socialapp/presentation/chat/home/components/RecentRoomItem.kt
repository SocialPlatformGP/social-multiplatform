package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.presentation.chat.home.ChatHomeUiEvent
import io.github.aakira.napier.Napier

@Composable
fun RecentRoomItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    recentRoom: RecentRoom,
    currentUserId: String,
    event: (ChatHomeUiEvent) -> Unit
) {
    Surface(
        onClick = { event(ChatHomeUiEvent.OnRecentChatClick(recentRoom)) },
        modifier = modifier.fillMaxWidth(),
        color = if(isSelected) MaterialTheme.colorScheme.surface else Color.Transparent,
        tonalElevation = if (isSelected) 2.dp else 0.dp,
        shadowElevation = if (isSelected) 2.dp else 0.dp
    ) {
        Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RecentChatImage(
                currentUserId = currentUserId, recentRoom = recentRoom
            )
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight().padding(start = 8.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                val title = if (recentRoom.isPrivate && recentRoom.senderId == currentUserId) {
                    recentRoom.receiverName
                } else {
                    recentRoom.senderName
                }
                RecentChatTopRow(
                    title = title, lastMessageTime = recentRoom.lastMessageTime
                )
                RecentChatBottomRow(
                    senderName = recentRoom.lastMessageSenderName,
                    senderId = recentRoom.senderId,
                    currentUserId = currentUserId,
                    lastMessage = recentRoom.lastMessage,
                )
            }

        }
    }
}

