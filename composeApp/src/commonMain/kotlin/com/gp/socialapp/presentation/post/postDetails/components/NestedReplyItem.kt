package com.gp.socialapp.presentation.post.postDetails.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.gp.socialapp.data.post.source.remote.model.NestedReply
import com.gp.socialapp.presentation.post.feed.ReplyEvent

@Composable
fun LazyListScope.NestedReplyItem(comment: NestedReply, level: Int, onReplyEvent: (ReplyEvent)->Unit) {
    item {
//        val ltrLayoutDirection = remember { LayoutDirection.Ltr }
//        CompositionLocalProvider(LocalLayoutDirection provides ltrLayoutDirection) {
//            ReplyItem(comment, level,onReplyEvent)
//        }
        ReplyItem(comment, level,onReplyEvent)
    }
    RepliesList(comment.replies, level + 1,onReplyEvent)
}