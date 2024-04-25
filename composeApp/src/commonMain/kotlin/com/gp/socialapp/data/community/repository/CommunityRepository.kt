package com.gp.socialapp.data.community.repository

import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results

interface CommunityRepository {
    suspend fun createCommunity(
        community: Community,
        userId: String
    ): Results<Community, DataError.Network>
}