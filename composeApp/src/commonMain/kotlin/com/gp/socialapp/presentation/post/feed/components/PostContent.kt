package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.presentation.post.feed.PostEvent

@Composable
fun PostContent(
    title: String,
    body: String,
    attachments: List<PostFile>,
    moderationStatus: String,
    onPostEvent: (PostEvent) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        PostTitle(title)
        PostBody(body)
        Attachments(
            attachments = attachments,
            onPostEvent = onPostEvent
        )
    }
}