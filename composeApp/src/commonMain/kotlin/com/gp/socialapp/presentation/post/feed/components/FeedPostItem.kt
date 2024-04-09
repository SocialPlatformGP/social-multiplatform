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
import com.gp.socialapp.presentation.post.feed.PostEvent
import com.gp.socialapp.util.LocalDateTimeUtil.toYYYYMMDD
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun FeedPostItem(
    post: Post,
    onPostEvent: (PostEvent) -> Unit,
    currentUserID: String
) {
    Card(
        onClick = { onPostEvent(PostEvent.OnPostClicked(post)) },
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary)
    ) {
        println("\n\n\n\npost in FeedPostItem: $post\n\n\n\n")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            TopRow(
                imageUrl = post.authorPfp,
                userName = post.authorName,
                publishedAt = Instant.fromEpochSeconds(post.createdAt).toLocalDateTime(TimeZone.UTC).toYYYYMMDD(),
                onEditPostClicked = { onPostEvent(PostEvent.OnPostEdited(post)) },
                onDeletePostClicked = { onPostEvent(PostEvent.OnPostDeleted(post)) }
            )
            TagsFlowRow(
                selectedTags = post.tags.toSet(),
                onTagClicked = { onPostEvent(PostEvent.OnTagClicked(it)) }
            )
            PostContent(
                title = post.title,
                body = post.body,
                attachments = post.attachments,
                moderationStatus = post.moderationStatus,
                onPostEvent = onPostEvent
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp, end = 8.dp, bottom = 8.dp
                    ),
                thickness = 0.5.dp,
                color = Color.Gray
            )
            BottomRow(
                upVotes = post.upvoted,
                downVotes = post.downvoted,
                commentCount = post.replyCount,
                votes = post.votes,
                onUpVoteClicked = { onPostEvent(PostEvent.OnPostUpVoted(post)) },
                onDownVoteClicked = { onPostEvent(PostEvent.OnPostDownVoted(post)) },
                onCommentClicked = {
                    onPostEvent(PostEvent.OnCommentClicked(post.id))
                },
                filesCount = post.attachments.size,
                currentUserID = currentUserID,
                onShowFilesClicked = { onPostEvent(PostEvent.OnViewFilesAttachmentClicked(post.attachments)) },
                onShareClicked =  {onPostEvent(PostEvent.OnPostShareClicked(post))}
            )
        }
    }

}