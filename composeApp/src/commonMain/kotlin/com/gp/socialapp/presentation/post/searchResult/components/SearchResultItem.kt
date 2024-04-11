package com.gp.socialapp.presentation.post.searchResult.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.post.source.remote.model.Post

@Composable
fun SearchResultItem(
    modifier: Modifier = Modifier,
    item: Post,
    onPostClicked: (Post) -> Unit,
) {
    Card(
        onClick = { onPostClicked(item) },
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            ResultItemTopRow(
                imageUrl = item.authorPfp,
                userName = item.authorName,
                publishedAt = item.createdAt.toString(),
            )
            ResultItemContent(
                title = item.title,
                body = item.body,
                attachments = item.attachments
            )
            ResultItemBottomRow(
                voteCount = item.votes,
                replyCount = item.replyCount,
            )
        }
    }


}