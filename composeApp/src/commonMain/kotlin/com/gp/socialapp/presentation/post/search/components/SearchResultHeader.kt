package com.gp.socialapp.presentation.post.search.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchResultHeader(
    modifier: Modifier = Modifier,
    searchTerm: String,
    isTag: Boolean
) {
    Text(
        text = "Search results for: ${if (isTag) "#$searchTerm" else searchTerm}",
        maxLines = 1,
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier.fillMaxWidth()
    )
}