package com.gp.socialapp.data.community.source.remote

import com.gp.socialapp.data.community.model.CommunityMemberRequest
import com.gp.socialapp.data.community.source.remote.model.Community
import com.gp.socialapp.data.community.source.remote.model.request.CommunityRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.CommunityError
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Result.Companion.success
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
    override suspend fun createCommunity(request: CommunityRequest.CreateCommunity): Result<Community, CommunityError.CreateCommunity> {
        return try {
            val response = httpClient.post {
                endPoint("createCommunity")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                val community = response.body<Community>()
                success(community)
            } else {
                val serverError = response.body<CommunityError.CreateCommunity>()
                error(serverError)
            }
        } catch (e: Exception) {
            error(CommunityError.CreateCommunity.SERVER_ERROR)
        }
    }

    override suspend fun acceptCommunityRequest(request: CommunityRequest.AcceptCommunityRequest): Result<Unit, CommunityError.AcceptCommunityRequest> {
        return try {
            val response = httpClient.post {
                endPoint("acceptCommunityRequest")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val serverError = response.body<CommunityError.AcceptCommunityRequest>()
                error(serverError)
            }
        } catch (e: Exception) {
            error(CommunityError.AcceptCommunityRequest.SERVER_ERROR)
        }
    }

    override suspend fun declineCommunityRequest(request: CommunityRequest.DeclineCommunityRequest): Result<Unit, CommunityError.DeclineCommunityRequest> {
        return try {
            val response = httpClient.post {
                endPoint("declineCommunityRequest")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val serverError = response.body<CommunityError.DeclineCommunityRequest>()
                error(serverError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(CommunityError.DeclineCommunityRequest.SERVER_ERROR)
        }
    }

    override fun fetchCommunity(request: CommunityRequest.FetchCommunity): Flow<Result<Community, CommunityError.GetCommunity>> =
        flow {
            emit(Result.Loading)
            try {
                val response = httpClient.post {
                    endPoint("fetchCommunity")
                    setBody(request)
                }
                if (response.status == HttpStatusCode.OK) {
                    val community = response.body<Community>()
                    emit(success(community))
                } else {
                    val serverError = response.body<CommunityError.GetCommunity>()
                    emit(Result.Error(serverError))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(CommunityError.GetCommunity.SERVER_ERROR))
            }
        }

    override fun fetchCommunityMembersRequests(request: CommunityRequest.FetchCommunityMembersRequests): Flow<Result<List<CommunityMemberRequest>, CommunityError.GetCommunityMembers>> =
        flow {
            emit(Result.Loading)
            try {
                val response = httpClient.post {
                    endPoint("fetchCommunityMembersRequests")
                    setBody(request)
                }
                if (response.status == HttpStatusCode.OK) {
                    val community = response.body<List<CommunityMemberRequest>>()
                    emit(success(community))
                } else {
                    val serverError = response.body<CommunityError.GetCommunityMembers>()
                    emit(Result.Error(serverError))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(CommunityError.GetCommunityMembers.SERVER_ERROR))
            }
        }

    override suspend fun deleteCommunity(request: CommunityRequest.DeleteCommunity): Result<Unit, CommunityError.DeleteCommunity> {
        return try {
            println("deleteCommunity request: $request")
            val response = httpClient.post {
                endPoint("deleteCommunity")
                setBody(request)
            }
            println("deleteCommunity response: $response")
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val serverError = response.body<CommunityError.DeleteCommunity>()
                error(serverError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(CommunityError.DeleteCommunity.SERVER_ERROR)
        }
    }

    override suspend fun editCommunity(request: CommunityRequest.EditCommunity): Result<Unit, CommunityError.UpdateCommunity> {
        return try {
            println("editCommunity request: $request")
            val response = httpClient.post {
                endPoint("editCommunity")
                setBody(request)
            }
            println("editCommunity response: $response")
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val serverError = response.body<CommunityError.UpdateCommunity>()
                error(serverError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(CommunityError.UpdateCommunity.SERVER_ERROR)
        }
    }
}