package com.gp.socialapp.data.db.model

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.data.post.source.remote.model.PostFile.Companion.toPostFile
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.data.post.source.remote.model.Tag.Companion.toTag
import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class PostEntity(
    val replyCount: Int = 0,
    val authorName: String = "",
    val authorPfp: String = "",
    val id: String = "",
    val authorID: String = "",
    val createdAt: Int = LocalDateTime.now().second,
    val title: String = "",
    val body: String = "",
    val votes: Int = 0,
    val downvoted: String = "",
    val upvoted: String = "",
    val moderationStatus: String = "submitted",
    val editedStatus: Int = 0,
    val tags: String = "",
    val type: String = "general",
    val attachments: String = "",
    val lastModified: Int = LocalDateTime.now().second
){
    companion object{
        fun PostEntity.toPost(): Post {
            return with(this){
                Post(
                    replyCount = replyCount,
                    authorName = authorName,
                    authorPfp = authorPfp,
                    id = id,
                    authorID = authorID,
                    createdAt = Instant.fromEpochSeconds(createdAt.toLong()).toLocalDateTime(TimeZone.UTC),
                    title = title,
                    body = body,
                    votes = votes,
                    downvoted = downvoted.split(","),
                    upvoted = upvoted.split(","),
                    moderationStatus = moderationStatus,
                    editedStatus = editedStatus == 1,
                    tags = tags.split(",").map { it.toTag() },
                    type = type,
                    attachments = attachments.split(",").map { it.toPostFile() },
                    lastModified = Instant.fromEpochSeconds(lastModified.toLong()).toLocalDateTime(TimeZone.UTC)
                )
            }
        }
    }
}
