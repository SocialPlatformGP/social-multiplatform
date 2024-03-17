package com.gp.socialapp.presentation.post.feed

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.Result

data class FeedUiState(
    val posts :List<Post> = emptyList(),
    val isFeedLoaded: Result<Nothing> = Result.Idle,
    val isSortedByNewest: Boolean = true,
    val allTags: Set<Tag> = emptySet(),
    val selectedTags: Set<Tag> = emptySet()
)