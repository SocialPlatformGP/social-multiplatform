package com.gp.socialapp.data.community.repository

import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.data.community.source.local.CommunityLocalDataSource
import com.gp.socialapp.data.community.source.remote.CommunityRemoteDataSource
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.data.community.source.remote.model.request.CommunityRequest
import com.gp.socialapp.util.CommunityError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class CommunityRepositoryImpl(
    private val communityLocalDataSource: CommunityLocalDataSource,
    private val communityRemoteDataSource: CommunityRemoteDataSource
) : CommunityRepository {
    override suspend fun createCommunity(
        community: Community,
        userId: String
    ): Result<Community, CommunityError.CreateCommunity> {
        val request = CommunityRequest.CreateCommunity(community, userId)
        return communityRemoteDataSource.createCommunity(request)
    }

    override suspend fun acceptCommunityRequest(
        requestId: String
    ): Result<Unit, CommunityError.AcceptCommunityRequest> {
        val request = CommunityRequest.AcceptCommunityRequest(requestId)
        return communityRemoteDataSource.acceptCommunityRequest(request)
    }

    override suspend fun declineCommunityRequest(
        requestId: String
    ): Result<Unit, CommunityError.DeclineCommunityRequest> {
        val request = CommunityRequest.DeclineCommunityRequest(requestId)
        return communityRemoteDataSource.declineCommunityRequest(request)
    }

    override fun fetchCommunity(communityId: String): Flow<Result<Community, CommunityError.GetCommunity>> {
        val request = CommunityRequest.FetchCommunity(communityId)
        return communityRemoteDataSource.fetchCommunity(request)
    }

    override fun fetchCommunityMembersRequests(communityId: String): Flow<Result<List<CommunityMemberRequest>, CommunityError.GetCommunityMembers>> {
        val request = CommunityRequest.FetchCommunityMembersRequests(communityId)
        return communityRemoteDataSource.fetchCommunityMembersRequests(request)
    }

    override suspend fun deleteCommunity(communityId: String): Result<Unit, CommunityError.DeleteCommunity> {
        val request = CommunityRequest.DeleteCommunity(communityId)
        return communityRemoteDataSource.deleteCommunity(request)
    }

    override suspend fun editCommunity(community: Community): Result<Unit, CommunityError.UpdateCommunity> {
        val request = CommunityRequest.EditCommunity(community)
        println("CommunityRepositoryImpl editCommunity request: $request")
        return communityRemoteDataSource.editCommunity(request)
    }
}