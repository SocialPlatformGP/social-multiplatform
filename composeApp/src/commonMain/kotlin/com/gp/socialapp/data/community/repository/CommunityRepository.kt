package com.gp.socialapp.data.community.repository

import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.CommunityError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun createCommunity(
        community: Community,
        userId: String
    ): Result<Community, CommunityError>
    suspend fun acceptCommunityRequest(requestId: String): Result<Unit, CommunityError>
    suspend fun declineCommunityRequest(requestId: String): Result<Unit, CommunityError>
    fun fetchCommunity(communityId: String): Flow<Result<Community, CommunityError>>
    fun fetchCommunityMembersRequests(communityId: String): Flow<Result<List<CommunityMemberRequest>, CommunityError>>
    suspend fun deleteCommunity(communityId: String): Result<Unit, CommunityError>
    suspend fun editCommunity(community: Community): Result<Unit, CommunityError>
}