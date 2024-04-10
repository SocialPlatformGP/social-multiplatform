package com.gp.socialapp.presentation.post.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gp.socialapp.data.post.source.remote.model.Post

@Composable
fun SearchResultItem(
    modifier: Modifier = Modifier,
    item: Post,
    onPostClicked: (Post) -> Unit,
) {
    Column (
        modifier = modifier.fillMaxSize().clickable { onPostClicked(item) }
    ){
        ResultItemTopRow(
            imageUrl = item.authorPfp,
            userName = item.authorName,
            publishedAt = item.createdAt.toString(),
        )
        ResultItemContent(
            title = item.title,
            attachments = item.attachments
        )
        ResultItemBottomRow(
            voteCount = item.votes,
            replyCount = item.replyCount,
        )
    }

}