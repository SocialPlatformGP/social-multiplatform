package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PostTitle(title: String) {
    if (title.isEmpty()) return
    Text(
        text = title,
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp,
            bottom = 2.dp
        ),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSecondaryContainer
    )
}