package com.gp.socialapp.data.community.source.remote.model.request

import com.gp.socialapp.data.community.source.remote.model.Community
import kotlinx.serialization.Serializable

sealed class CommunityRequest {
    @Serializable
    data class CreateCommunity(
        val community: Community,
        val creatorId: String
    ) : CommunityRequest()
}