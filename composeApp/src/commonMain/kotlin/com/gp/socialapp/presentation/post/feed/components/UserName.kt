package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserName(
    userName: String,
) {
    Text(
        text = userName,
        modifier = Modifier
            .padding(
                start = 4.dp,
                end = 8.dp,
            ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        softWrap = true,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}