package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.toColor

@Composable
fun TagItem(
    onTagClicked: (Tag) -> Unit,
    tag: Tag
) {
    SuggestionChip(
        onClick = {
            onTagClicked(tag)
        },
        shape = RoundedCornerShape(8.dp),
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = tag.hexColor.toColor(),
        ),
        label = {
            Text(
                text = tag.label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        },
        modifier = Modifier
            .sizeIn(
                maxHeight = 24.dp,
            )
            .padding(2.dp)

    )
}
