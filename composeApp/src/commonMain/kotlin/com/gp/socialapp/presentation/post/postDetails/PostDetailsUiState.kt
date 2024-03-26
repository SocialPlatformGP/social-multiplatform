package com.gp.socialapp.presentation.post.postDetails

import com.gp.socialapp.data.post.source.remote.model.Post

data class PostDetailsUiState(
    val post: Post = Post(),
)
