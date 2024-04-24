package com.gp.socialapp.data.community.repository

import com.gp.socialapp.data.community.source.local.CommunityLocalDataSource
import com.gp.socialapp.data.community.source.remote.CommunityRemoteDataSource
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.data.community.source.remote.model.request.CommunityRequest
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results

class CommunityRepositoryImpl(
    private val communityLocalDataSource: CommunityLocalDataSource,
    private val communityRemoteDataSource: CommunityRemoteDataSource
) : CommunityRepository {
    override suspend fun createCommunity(community: Community, userId: String): Results<Community, DataError> {
        val request = CommunityRequest.CreateCommunity(community, userId)
        return communityRemoteDataSource.createCommunity(request)
    }
}