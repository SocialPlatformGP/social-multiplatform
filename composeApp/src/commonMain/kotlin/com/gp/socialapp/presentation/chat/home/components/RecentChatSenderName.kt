package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RecentChatSenderName(senderName: String, isPrivate: Boolean) {
    if (!isPrivate && senderName.isNotEmpty()) {
        Text(
            text = "$senderName: ",
        )
    }
}