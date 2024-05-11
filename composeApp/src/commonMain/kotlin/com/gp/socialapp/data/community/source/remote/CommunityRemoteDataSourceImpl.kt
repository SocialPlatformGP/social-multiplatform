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
    override suspend fun createCommunity(request: CommunityRequest.CreateCommunity): Result<Community, CommunityError> {
        return try {
            val response = httpClient.post {
                endPoint("createCommunity")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                val community = response.body<Community>()
                success(community)
            } else {
                val serverError = response.body<CommunityError>()
                error(serverError)
            }
        } catch (e: Exception) {
            error(CommunityError.SERVER_ERROR)
        }
    }

    override suspend fun acceptCommunityRequest(request: CommunityRequest.AcceptCommunityRequest): Result<Unit, CommunityError> {
        return try {
            val response = httpClient.post {
                endPoint("acceptCommunityRequest")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val serverError = response.body<CommunityError>()
                error(serverError)
            }
        } catch (e: Exception) {
            error(CommunityError.SERVER_ERROR)
        }
    }

    override suspend fun declineCommunityRequest(request: CommunityRequest.DeclineCommunityRequest): Result<Unit, CommunityError> {
        return try {
            val response = httpClient.post {
                endPoint("declineCommunityRequest")
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val serverError = response.body<CommunityError>()
                error(serverError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(CommunityError.SERVER_ERROR)
        }
    }

    override fun fetchCommunity(request: CommunityRequest.FetchCommunity): Flow<Result<Community, CommunityError>> =
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
                    val serverError = response.body<CommunityError>()
                    emit(Result.Error(serverError))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(CommunityError.SERVER_ERROR))
            }
        }

    override fun fetchCommunityMembersRequests(request: CommunityRequest.FetchCommunityMembersRequests): Flow<Result<List<CommunityMemberRequest>, CommunityError>> =
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
                    val serverError = response.body<CommunityError>()
                    emit(Result.Error(serverError))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(CommunityError.SERVER_ERROR))
            }
        }

    override suspend fun deleteCommunity(request: CommunityRequest.DeleteCommunity): Result<Unit, CommunityError> {
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
                val serverError = response.body<CommunityError>()
                error(serverError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(CommunityError.SERVER_ERROR)
        }
    }

    override suspend fun editCommunity(request: CommunityRequest.EditCommunity): Result<Unit, CommunityError> {
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
                val serverError = response.body<CommunityError>()
                error(serverError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(CommunityError.SERVER_ERROR)
        }
    }
}