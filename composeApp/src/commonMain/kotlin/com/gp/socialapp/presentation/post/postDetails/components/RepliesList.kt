package com.gp.socialapp.presentation.post.postDetails.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import com.gp.socialapp.data.post.source.remote.model.NestedReply
import com.gp.socialapp.presentation.post.feed.ReplyEvent


fun LazyListScope.RepliesList(replies: List<NestedReply>, level: Int = 0, onReplyEvent: (ReplyEvent)->Unit) {
    replies.forEach { reply ->
        NestedReplyItem(reply, level ,onReplyEvent)
    }
}