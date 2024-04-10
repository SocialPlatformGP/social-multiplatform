package com.gp.socialapp.presentation.post.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ResultItemBottomRow(
    modifier: Modifier = Modifier,
    voteCount: Int,
    replyCount: Int,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier,
    ) {
        Text(
            text = "$voteCount votes\t.\t$replyCount replies",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
        )
    }
}