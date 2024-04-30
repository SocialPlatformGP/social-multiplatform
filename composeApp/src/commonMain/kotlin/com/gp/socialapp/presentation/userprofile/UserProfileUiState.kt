package com.gp.socialapp.presentation.userprofile

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.post.source.remote.model.Post

data class UserProfileUiState(
    val isLoading: Boolean = false,
    val user: User = User(),
    val posts: List<Post> = emptyList(),
    val error: String? = null,
    val editingMode: Boolean = false
)