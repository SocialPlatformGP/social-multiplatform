package com.gp.socialapp.presentation.post.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RecentSearchesSection(
    modifier: Modifier = Modifier,
    items: List<String>,
    onItemClick: (String) -> Unit,
    onDeleteItem: (String) -> Unit,
) {
    if(items.isNotEmpty()){
        Column{
            Text(
                text = "Recent Searches",
                style = MaterialTheme.typography.titleMedium)
            items.forEach { item ->
                RecentSearchItem(
                    item = item,
                    onItemClick = onItemClick,
                    onDeleteItem = onDeleteItem
                )
            }
        }

    }
}