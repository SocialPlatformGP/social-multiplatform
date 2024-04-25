package com.gp.socialapp.data.community.source.remote

import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.data.community.source.remote.model.request.CommunityRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class CommunityRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : CommunityRemoteDataSource {
    override suspend fun createCommunity(request: CommunityRequest.CreateCommunity): Results<Community, DataError.Network> {
        return try {
            val response = httpClient.post {
                endPoint("createCommunity")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                val community = response.body<Community>()
                Results.success(community)
            } else {
                val error = response.body<DataError.Network>()
                Results.failure(error)
            }
        } catch (e: Exception) {
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }
    }
}