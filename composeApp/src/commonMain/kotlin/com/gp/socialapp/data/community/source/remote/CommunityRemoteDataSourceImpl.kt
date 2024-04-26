package com.gp.socialapp.data.community.source.remote

import com.gp.socialapp.data.community.model.CommunityMemberRequest
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
                Results.failure(DataError.Network.SERVER_ERROR)
            }
        } catch (e: Exception) {
            Results.failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }
    }

    override suspend fun acceptCommunityRequest(request: CommunityRequest.AcceptCommunityRequest): Results<Unit, DataError.Network> {
        return try {
            val response = httpClient.post {
                endPoint("acceptCommunityRequest")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                Results.success(Unit)
            } else {
                Results.failure(DataError.Network.SERVER_ERROR)
            }
        } catch (e: Exception) {
            Results.failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }
    }

    override suspend fun declineCommunityRequest(request: CommunityRequest.DeclineCommunityRequest): Results<Unit, DataError.Network> {
        return try {
            val response = httpClient.post {
                endPoint("declineCommunityRequest")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                Results.success(Unit)
            } else {
                Results.failure(DataError.Network.SERVER_ERROR)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }
    }

    override fun fetchCommunity(request: CommunityRequest.FetchCommunity): Flow<Results<Community, DataError.Network>> =
        flow {
            emit(Results.Loading)
            try {
                val response = httpClient.post {
                    endPoint("fetchCommunity")
                    setBody(request)
                }
                if (response.status == HttpStatusCode.OK) {
                    val community = response.body<Community>()
                    emit(Results.success(community))
                } else {
                    emit(Results.failure(DataError.Network.SERVER_ERROR))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Results.failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
            }
        }

    override fun fetchCommunityMembersRequests(request: CommunityRequest.FetchCommunityMembersRequests): Flow<Results<List<CommunityMemberRequest>, DataError.Network>> =
        flow {
            emit(Results.Loading)
            try {
                val response = httpClient.post {
                    endPoint("fetchCommunityMembersRequests")
                    setBody(request)
                }
                if (response.status == HttpStatusCode.OK) {
                    val community = response.body<List<CommunityMemberRequest>>()
                    emit(Results.success(community))
                } else {
                    emit(Results.failure(DataError.Network.SERVER_ERROR))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Results.failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
            }
        }
}