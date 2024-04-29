package com.gp.socialapp.data.community.source.remote.model

typealias CommunityId = String

@kotlinx.serialization.Serializable
data class UserCommunities(
    val id: String = "",
    val groups: List<CommunityId> = emptyList()
)