package com.gp.socialapp.presentation.post.create.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gp.socialapp.data.post.source.remote.model.Tag

@Composable
fun TagsRow(
    tags: List<Tag>,
    onTagClick: (Tag) -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(tags) { tag ->
            AssistChip(
                label = { Text(tag.label) },
                onClick = {
                    onTagClick(tag)
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color(tag.intColor),
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = null,
                    )
                },
            )
        }

    }
}