package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColumnScope.MessageTextWithTimestamp(
    content: String,
    timestamp: String,
) {
    if (content.isNotBlank()) {
        Text(
            text = content,
            fontSize = 14.sp,
            modifier = Modifier.padding(0.dp)
        )
    }
    Text(
        text = timestamp,
        fontWeight = FontWeight.Light,
        fontSize = 10.sp,
        modifier = Modifier
            .align(Alignment.End)
    )
}