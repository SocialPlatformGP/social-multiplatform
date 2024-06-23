package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.gp.socialapp.presentation.chat.chatroom.components.adaptive

@Composable
fun RecentChatBottomRow(
    senderName: String,
    lastMessage: String,
    senderId: String,
    currentUserId: String
) {
    val text = if(senderId == currentUserId) {
        "You: $lastMessage"
    } else {
        "$senderName: $lastMessage"
    }
    Text(
        text = text.adaptive(35),
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Normal,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
    )
}