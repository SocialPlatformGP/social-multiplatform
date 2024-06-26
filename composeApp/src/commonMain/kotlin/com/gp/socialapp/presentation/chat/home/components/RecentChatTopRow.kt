package com.gp.socialapp.presentation.chat.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import korlibs.time.DateTimeTz

@Composable
fun RecentChatTopRow(
    title: String,
    lastMessageTime: DateTimeTz,
) {
    Row(
        Modifier.fillMaxWidth()
    ) {
        RcentChatTitle(title)
        Spacer(modifier = Modifier.weight(1f))
        RecentChatDate(lastMessageTime)
    }
}