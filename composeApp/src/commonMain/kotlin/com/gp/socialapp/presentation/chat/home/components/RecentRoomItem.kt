package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.chat.model.RecentRoom
import com.gp.socialapp.presentation.chat.home.ChatHomeUiEvent

@Composable
fun RecentRoomItem(
    modifier: Modifier = Modifier,
    recentRoom: RecentRoom,
    currentUserId: String,
    event: (ChatHomeUiEvent) -> Unit
) {
    Card(
        onClick = { event(ChatHomeUiEvent.OnRecentChatClick(recentRoom)) },
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RecentChatImage(
                currentUserId = currentUserId,
                recentRoom = recentRoom
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 8.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                val title = if(recentRoom.isPrivate && recentRoom.senderId == currentUserId) {
                    recentRoom.receiverName
                } else {
                    recentRoom.senderName
                }
                RecentChatTopRow(
                    title = title,
                    lastMessageTime = recentRoom.lastMessageTime
                )
                RecentChatBottomRow(
                    senderName = recentRoom.senderName,
                    lastMessage = recentRoom.lastMessage,
                    isPrivate = recentRoom.isPrivate
                )
            }
        }
    }


}

