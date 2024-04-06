package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostRequest
import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.data.post.source.remote.model.ReplyRequest
import com.gp.socialapp.util.AppConstants
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class ReplyRemoteDataSourceImpl: ReplyRemoteDataSource {
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
                isLenient = true
                encodeDefaults = true
            })
        }
    }

    private fun HttpRequestBuilder.endPoint(path: String) {
        url {
            takeFrom(AppConstants.BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)
        }
    }
    override suspend fun createReply(request: ReplyRequest.CreateRequest): Result<Nothing> {
        try {
            val response = httpClient.post {
                endPoint("createReply")
                setBody(
                    request
                )
            }
            val message = response.bodyAsText()
            if (response.status == HttpStatusCode.OK) {
                return Result.Success
            } else {
                return Result.Error(message)
            }
        } catch (e: Exception) {
            Napier.e("createReply: ${e.message}")
            return Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    override fun fetchReplies(request: ReplyRequest.FetchRequest): Flow<Result<List<Reply>>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.post {
                endPoint("fetchReplies")
                setBody(
                    request
                )
            }
            println("response: ${response.status}")
            if (response.status == HttpStatusCode.OK) {
                val replies = response.body<List<Reply>>()
                emit(Result.SuccessWithData(replies))
            } else {
                emit(Result.Error("An error occurred"))
            }
        } catch (e: Exception) {
            println("fetchReplies: ${e.message}")
            emit(Result.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun updateReply(request: ReplyRequest.UpdateRequest): Result<Nothing> {
        try {
            val response = httpClient.post {
                endPoint("updateReply")
                setBody(
                    request
                )
            }
            val message = response.bodyAsText()
            if (response.status == HttpStatusCode.OK) {
                return Result.Success
            } else {
                return Result.Error(message)
            }
        } catch (e: Exception) {
            Napier.e("createReply: ${e.message}")
            return Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun deleteReply(request: ReplyRequest.DeleteRequest): Result<Nothing> {
        try {
            val response = httpClient.post {
                endPoint("deleteReply")
                setBody(
                    request
                )
            }
            val message = response.bodyAsText()
            return if (response.status == HttpStatusCode.OK) {
                Result.Success
            } else {
                Result.Error(message)
            }
        } catch (e: Exception) {
            return Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun upvoteReply(request: ReplyRequest.UpvoteRequest) : Result<Nothing>{
        try {
            val response = httpClient.post {
                endPoint("upvoteReply")
                setBody(
                    request
                )
            }
            val message = response.bodyAsText()
            return if (response.status == HttpStatusCode.OK) {
                Result.Success
            } else {
                Result.Error(message)
            }
        } catch (e: Exception) {
            return Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun downvoteReply(request: ReplyRequest.DownvoteRequest): Result<Nothing> {
        try {
            val response = httpClient.post {
                endPoint("downvoteReply")
                setBody(
                    request
                )
            }
            val message = response.bodyAsText()
            return if (response.status == HttpStatusCode.OK) {
                Result.Success
            } else {
                Result.Error(message)
            }
        } catch (e: Exception) {
            return Result.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun reportReply(request: ReplyRequest.ReportRequest): Result<Nothing> {
        try {
            val response = httpClient.post {
                endPoint("reportReply")
                setBody(
                    request
                )
            }
            val message = response.bodyAsText()
            return if (response.status == HttpStatusCode.OK) {
                Result.Success
            } else {
                Result.Error(message)
            }
        } catch (e: Exception) {
            return Result.Error(e.message ?: "An unknown error occurred")
        }
    }

//    override suspend fun getReplyCountByPostId(postId: String): Result<Int>{
//        TODO("Not yet implemented")
//    }
}