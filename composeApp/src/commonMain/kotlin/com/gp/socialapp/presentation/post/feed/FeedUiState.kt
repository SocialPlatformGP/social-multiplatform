package com.gp.socialapp.presentation.post.feed

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.util.Result

data class FeedUiState(
    val posts :List<Post> = emptyList(),
    val result: Result<Nothing> = Result.Idle
)
