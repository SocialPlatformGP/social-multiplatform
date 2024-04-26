package com.gp.socialapp.data.community.source.remote

import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.data.community.source.remote.model.request.CommunityRequest
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

interface CommunityRemoteDataSource {
    suspend fun createCommunity(request: CommunityRequest.CreateCommunity): Results<Community, DataError.Network>
    suspend fun acceptCommunityRequest(request: CommunityRequest.AcceptCommunityRequest): Results<Unit, DataError.Network>
    suspend fun declineCommunityRequest(request: CommunityRequest.DeclineCommunityRequest): Results<Unit, DataError.Network>
    fun fetchCommunity(request: CommunityRequest.FetchCommunity): Flow<Results<Community, DataError.Network>>
    fun fetchCommunityMembersRequests(request: CommunityRequest.FetchCommunityMembersRequests): Flow<Results<List<CommunityMemberRequest>, DataError.Network>>
}