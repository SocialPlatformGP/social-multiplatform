package com.gp.socialapp.data.community.repository

import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.data.community.source.local.CommunityLocalDataSource
import com.gp.socialapp.data.community.source.remote.CommunityRemoteDataSource
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.data.community.source.remote.model.request.CommunityRequest
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

class CommunityRepositoryImpl(
    private val communityLocalDataSource: CommunityLocalDataSource,
    private val communityRemoteDataSource: CommunityRemoteDataSource
) : CommunityRepository {
    override suspend fun createCommunity(
        community: Community,
        userId: String
    ): Results<Community, DataError.Network> {
        val request = CommunityRequest.CreateCommunity(community, userId)
        return communityRemoteDataSource.createCommunity(request)
    }

    override suspend fun acceptCommunityRequest(
        requestId: String
    ): Results<Unit, DataError.Network> {
        val request = CommunityRequest.AcceptCommunityRequest(requestId)
        return communityRemoteDataSource.acceptCommunityRequest(request)
    }

    override suspend fun declineCommunityRequest(
        requestId: String
    ): Results<Unit, DataError.Network> {
        val request = CommunityRequest.DeclineCommunityRequest(requestId)
        return communityRemoteDataSource.declineCommunityRequest(request)
    }

    override fun fetchCommunity(communityId: String): Flow<Results<Community, DataError.Network>> {
        val request = CommunityRequest.FetchCommunity(communityId)
        return communityRemoteDataSource.fetchCommunity(request)
    }

    override fun fetchCommunityMembersRequests(communityId: String): Flow<Results<List<CommunityMemberRequest>, DataError.Network>> {
        val request = CommunityRequest.FetchCommunityMembersRequests(communityId)
        return communityRemoteDataSource.fetchCommunityMembersRequests(request)
    }

    override suspend fun deleteCommunity(communityId: String): Results<Unit, DataError.Network> {
        val request = CommunityRequest.DeleteCommunity(communityId)
        return communityRemoteDataSource.deleteCommunity(request)
    }

    override suspend fun editCommunity(community: Community): Results<Unit, DataError.Network> {
        val request = CommunityRequest.EditCommunity(community)
        println("CommunityRepositoryImpl editCommunity request: $request")
        return communityRemoteDataSource.editCommunity(request)
    }
}