package com.gp.socialapp.presentation.community.communityhome

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.source.remote.model.Community

data class CommunityHomeUiState(
    val communityId: String = "",
    val currentUser: User = User(),
    val userCommunities: List<Community> = listOf(),
    val isLoggedOut: Boolean = false
)
