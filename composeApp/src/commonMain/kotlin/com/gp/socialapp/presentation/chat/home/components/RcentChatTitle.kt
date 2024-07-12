package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.gp.socialapp.presentation.chat.chatroom.components.adaptive

@Composable
fun RecentChatTitle(title: String) {
    Text(
        text = title.adaptive(18),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}