package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RecentChatBottomRow(
    senderName: String, lastMessage: String, isPrivate: Boolean
) {
    Row(
        Modifier.fillMaxWidth()
    ) {
        RecentChatSenderName(senderName, isPrivate)
        RecentChatLastMessage(lastMessage)
    }
}