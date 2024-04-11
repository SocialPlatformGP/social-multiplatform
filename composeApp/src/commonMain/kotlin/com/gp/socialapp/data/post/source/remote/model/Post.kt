package com.gp.socialapp.data.post.source.remote.model

import com.gp.socialapp.data.post.source.local.model.PostEntity
import com.gp.socialapp.data.post.source.remote.model.PostFile.Companion.toDbString
import com.gp.socialapp.data.post.source.remote.model.Tag.Companion.toDbString
import com.gp.socialapp.data.post.util.PostPopularityUtils
import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@kotlinx.serialization.Serializable
data class Post(
    val replyCount: Int = 0,
    val authorName: String = "",
    val authorPfp: String = "",
    val id: String = "",
    val authorID: String = "",
    val createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds,
    val title: String = "",
    val body: String = "",
    val votes: Int = 0,
    val downvoted: List<String> = emptyList(),
    val upvoted: List<String> = emptyList(),
    val moderationStatus: String = "submitted",
    val editedStatus: Boolean = false,
    val tags: List<Tag> = emptyList(),
    val type: String = "general",
    val attachments: List<PostFile> = emptyList(),
    val lastModified: Long = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds
) {
    companion object {
        val sortByVotes = compareByDescending<Post> {
            PostPopularityUtils.calculateInteractionValue(
                it.votes,
                it.replyCount
            )
        }

        fun Post.toEntity(): PostEntity {
            return PostEntity(
                replyCount = replyCount,
                authorName = authorName,
                authorPfp = authorPfp,
                id = id,
                authorID = authorID,
                createdAt = createdAt,
                title = title,
                body = body,
                votes = votes,
                downvoted = downvoted.joinToString(separator = ","),
                upvoted = upvoted.joinToString(separator = ","),
                moderationStatus = moderationStatus,
                editedStatus = if (editedStatus) 1 else 0,
                tags = tags.joinToString(separator = ",") { it.toDbString() },
                type = type,
                attachments = attachments.joinToString(separator = ",") { it.toDbString() },
                lastModified = lastModified
            )
        }
//        val sortByDate = compareByDescending<Post>{convertStringToDate(it.publishedAt)}
    }
}
