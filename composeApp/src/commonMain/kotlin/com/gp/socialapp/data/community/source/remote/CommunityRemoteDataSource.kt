package com.gp.socialapp.data.community.source.remote

import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.data.community.source.remote.model.request.CommunityRequest
import com.gp.socialapp.util.CommunityError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface CommunityRemoteDataSource {
    suspend fun createCommunity(request: CommunityRequest.CreateCommunity): Result<Community,CommunityError>
    suspend fun acceptCommunityRequest(request: CommunityRequest.AcceptCommunityRequest): Result<Unit, CommunityError>
    suspend fun declineCommunityRequest(request: CommunityRequest.DeclineCommunityRequest): Result<Unit, CommunityError>
    fun fetchCommunity(request: CommunityRequest.FetchCommunity): Flow<Result<Community, CommunityError>>
    fun fetchCommunityMembersRequests(request: CommunityRequest.FetchCommunityMembersRequests): Flow<Result<List<CommunityMemberRequest>, CommunityError>>
    suspend fun deleteCommunity(request: CommunityRequest.DeleteCommunity): Result<Unit, CommunityError>
    suspend fun editCommunity(request: CommunityRequest.EditCommunity): Result<Unit, CommunityError>
}