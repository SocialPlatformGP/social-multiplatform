package com.gp.socialapp.presentation.chat.chatroom.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageUserName(
    modifier: Modifier = Modifier, name: String, onUserClick: () -> Unit
) {
    ClickableText(
        text = AnnotatedString(name),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        onClick = { onUserClick() },
        modifier = modifier.padding(horizontal = 4.dp)
    )
}