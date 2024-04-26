package com.gp.socialapp.data.community.repository

import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.data.community.source.remote.model.request.CommunityRequest
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun createCommunity(
        community: Community,
        userId: String
    ): Results<Community, DataError.Network>
    suspend fun acceptCommunityRequest(communityId: String, userId: String): Results<Unit, DataError.Network>
    suspend fun declineCommunityRequest(communityId: String, userId: String): Results<Unit, DataError.Network>
    fun fetchCommunity(communityId: String): Flow<Results<Community, DataError.Network>>
    fun fetchCommunityMembersRequests(communityId: String): Flow<Results<List<CommunityMemberRequest>, DataError.Network>>
}