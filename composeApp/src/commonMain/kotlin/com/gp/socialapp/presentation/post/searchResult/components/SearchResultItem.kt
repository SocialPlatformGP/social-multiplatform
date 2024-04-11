package com.gp.socialapp.presentation.post.searchResult.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    Column (
        modifier = modifier.fillMaxWidth().clickable { onPostClicked(item) }.padding(8.dp)
    ){
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