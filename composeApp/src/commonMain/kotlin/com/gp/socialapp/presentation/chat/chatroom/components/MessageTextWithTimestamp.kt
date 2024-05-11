package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun MessageTextWithTimestamp(
    content: String,
    timestamp: String,
) {
    Column {
        if (content.isNotBlank()) {
            Text(
                text = content,
                fontSize = 14.sp,
//            modifier = Modifier.padding(top = 4.dp, start = 4.dp, end = 4.dp)
            )
        }
        Text(
            text = timestamp,
            fontSize = 10.sp,
            modifier = Modifier
                .align(Alignment.End)
//            .padding(end = 4.dp, bottom = 4.dp)
        )
    }
}