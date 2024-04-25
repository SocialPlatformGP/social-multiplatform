package com.gp.socialapp.data.community.source.remote

import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.data.community.source.remote.model.request.CommunityRequest
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results

interface CommunityRemoteDataSource {
    suspend fun createCommunity(request: CommunityRequest.CreateCommunity): Results<Community, DataError.Network>
}