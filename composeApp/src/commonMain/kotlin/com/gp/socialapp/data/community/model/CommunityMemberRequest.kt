package com.gp.socialapp.data.community.model

import kotlinx.serialization.Serializable

@Serializable
data class CommunityMemberRequest(
    val id: String = "",
    val communityId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userAvatar: String = ""
)
