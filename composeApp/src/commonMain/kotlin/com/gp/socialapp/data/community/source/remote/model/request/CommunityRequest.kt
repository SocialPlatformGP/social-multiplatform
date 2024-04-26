package com.gp.socialapp.data.community.source.remote.model.request

import com.gp.socialapp.data.community.source.remote.model.Community
import kotlinx.serialization.Serializable

sealed class CommunityRequest {
    @Serializable
    data class CreateCommunity(
        val community: Community,
        val creatorId: String
    ) : CommunityRequest()

    @Serializable
    data class LogoutCommunity(
        val id: String,
        val selectedCommunityId: String
    ) : CommunityRequest()

    data class AcceptCommunityRequest (
        val communityId: String,
        val userId: String
    ) : CommunityRequest()
    data class DeclineCommunityRequest (
        val communityId: String,
        val userId: String
    ) : CommunityRequest()
    data class FetchCommunityMembersRequests(
        val communityId: String
    ) : CommunityRequest()
    data class FetchCommunity(
        val communityId: String
    ) : CommunityRequest()
}