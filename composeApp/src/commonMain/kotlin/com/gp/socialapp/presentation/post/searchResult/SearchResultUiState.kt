package com.gp.socialapp.presentation.post.searchResult

import com.gp.socialapp.data.post.source.remote.model.Post

data class SearchResultUiState(
    val posts: List<Post> = emptyList(),
)
