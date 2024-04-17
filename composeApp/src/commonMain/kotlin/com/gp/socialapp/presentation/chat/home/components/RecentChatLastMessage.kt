package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RecentChatLastMessage(lastMessage: String) {
    Text(
        text = lastMessage,
    )
}