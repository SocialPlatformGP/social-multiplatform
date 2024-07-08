package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.presentation.post.feed.PostEvent
import com.gp.socialapp.util.LocalDateTimeUtil.getPostDate
import com.gp.socialapp.util.ModerationSafety

@Composable
fun FeedPostItem(
    post: Post, onPostEvent: (PostEvent) -> Unit, currentUserID: String
) {
    Card(
        onClick = { onPostEvent(PostEvent.OnPostClicked(post)) },
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary)
    ) {
        if (post.moderationStatus == ModerationSafety.UNSAFE_TITLE.name || post.moderationStatus == ModerationSafety.UNSAFE_BODY.name || post.moderationStatus == ModerationSafety.UNSAFE_IMAGE.name) {
            UnsafePostItem(post,onPostEvent, currentUserID)
        } else {
            Column(
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            ) {
                TopRow(
                    imageUrl = post.authorPfp,
                    userName = post.authorName,
                    publishedAt = post.createdAt.getPostDate(),
                    onEditPostClicked = { onPostEvent(PostEvent.OnPostEdited(post)) },
                    onDeletePostClicked = { onPostEvent(PostEvent.OnPostDeleted(post)) },
                    onReportPostClicked = { onPostEvent(PostEvent.OnPostReported(post)) },
                    onUserClick = { onPostEvent(PostEvent.OnPostAuthorClicked(post.authorID)) },
                    isAuthor = post.authorID == currentUserID
                )
                TagsFlowRow(selectedTags = post.tags.toSet(),
                    onTagClicked = { onPostEvent(PostEvent.OnTagClicked(it)) })
                PostContent(
                    title = post.title,
                    body = post.body,
                    attachments = post.attachments,
                    moderationStatus = post.moderationStatus,
                    onPostEvent = onPostEvent
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().padding(
                        start = 8.dp, end = 8.dp, bottom = 8.dp
                    ), thickness = 0.5.dp, color = Color.Gray
                )
                BottomRow(upVotes = post.upvoted,
                    downVotes = post.downvoted,
                    commentCount = post.replyCount,
                    votes = post.votes,
                    onUpVoteClicked = { onPostEvent(PostEvent.OnPostUpVoted(post)) },
                    onDownVoteClicked = { onPostEvent(PostEvent.OnPostDownVoted(post)) },
                    onCommentClicked = {
                        onPostEvent(PostEvent.OnCommentClicked(post))
                    },
                    filesCount = (post.attachments.filter { MimeType.getMimeTypeFromFileName(it.name) !is MimeType.Image }).size,
                    currentUserID = currentUserID,
                    onShowFilesClicked = { onPostEvent(PostEvent.OnViewFilesAttachmentClicked(post.attachments)) },
                    onShareClicked = { onPostEvent(PostEvent.OnPostShareClicked(post)) })
            }
        }
    }

}

@Composable
fun UnsafePostItem(post: Post, onPostEvent: (PostEvent) -> Unit, currentUserID: String){
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        TopRow(
            imageUrl = post.authorPfp,
            userName = post.authorName,
            publishedAt = post.createdAt.getPostDate(),
            onEditPostClicked = { },
            onDeletePostClicked = { onPostEvent(PostEvent.OnPostDeleted(post)) },
            onReportPostClicked = { },
            onUserClick = { onPostEvent(PostEvent.OnPostAuthorClicked(post.authorID)) },
            isAuthor = post.authorID == currentUserID
        )
        PostContent(
            title = "",
            body = "This post has been flagged as unsafe because it contains inappropriate content.",
            attachments = emptyList(),
            moderationStatus = post.moderationStatus,
            onPostEvent = onPostEvent
        )
    }
}
