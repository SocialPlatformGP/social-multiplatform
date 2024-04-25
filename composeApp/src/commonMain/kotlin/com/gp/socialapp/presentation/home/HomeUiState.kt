package com.gp.socialapp.presentation.home

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.source.remote.model.Community

data class HomeUiState(
    val loading: Boolean = false,
    val communities: List<Community> = emptyList(),
    val error: String? = null,
    val user: User = User(),
    val selectedCommunityId: String = "",
    val loggedOut: Boolean = false
)