package com.gp.socialapp.presentation.community.communitymembers

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.community.model.CommunityMemberRequest

data class CommunityMembersUiState(
    val currentUserId: String = "",
    val communityId: String = "",
    val communityName: String = "",
    val members: List<User> = emptyList(),
    val admins: List<String> = emptyList(),
    val requests: List<CommunityMemberRequest> = emptyList(),
)
