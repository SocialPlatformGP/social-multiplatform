package com.gp.socialapp.data.post.source.remote.model

import com.gp.socialapp.data.post.util.PostPopularityUtils

@kotlinx.serialization.Serializable
data class Post(
    val replyCount: Int = 0,
    val userName: String = "",
    val userPfp: String = "",
    val id: String = "",
    val authorEmail: String = "",
    val publishedAt: String = "",
    val title: String = "",
    val body: String = "",
    val votes: Int = 0,
    val downvoted: List<String> = emptyList(),
    val upvoted: List<String> = emptyList(),
    val moderationStatus: String = "submitted",
    val editStatus: Boolean = false,
    val tags: List<Tag> = emptyList(),
    val type: String = "all",
    val attachments: List<PostFile> = emptyList()
) {
    companion object {
        val sortByVotes = compareByDescending<Post> {
            PostPopularityUtils.calculateInteractionValue(
                it.votes,
                it.replyCount
            )
        }
//        val sortByDate = compareByDescending<Post>{convertStringToDate(it.publishedAt)}
    }
}
