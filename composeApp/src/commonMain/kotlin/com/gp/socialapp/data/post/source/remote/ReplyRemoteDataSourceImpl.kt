package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.data.post.source.remote.model.ReplyRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.ReplyError
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Result.Companion.success
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReplyRemoteDataSourceImpl(
    private val client: HttpClient
) : ReplyRemoteDataSource {

    override suspend fun createReply(request: ReplyRequest.CreateRequest): Result<Unit, ReplyError.InsertReply> =
        try {
            val response = client.post {
                endPoint("createReply")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val serverError = response.body<ReplyError.InsertReply>()
                error(serverError)

            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(ReplyError.InsertReply.SERVER_ERROR)
        }


    override fun fetchReplies(request: ReplyRequest.FetchRequest): Flow<Result<List<Reply>, ReplyError.GetReplies>> =
        flow {
            emit(Result.Loading)
            try {
                val response = client.post {
                    endPoint("fetchReplies")
                    setBody(
                        request
                    )
                }
                if (response.status == HttpStatusCode.OK) {
                    val replies = response.body<List<Reply>>()
                    emit(Result.Success(replies))
                } else {
                    val error = response.body<ReplyError.GetReplies>()
                    emit(Result.Error(error))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(ReplyError.GetReplies.SERVER_ERROR))
            }
        }

    override suspend fun updateReply(request: ReplyRequest.UpdateRequest): Result<Unit, ReplyError.UpdateReply> =
        try {
            val response = client.post {
                endPoint("updateReply")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val serverError = response.body<ReplyError.UpdateReply>()
                error(serverError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(ReplyError.UpdateReply.SERVER_ERROR)
        }


    override suspend fun deleteReply(request: ReplyRequest.DeleteRequest): Result<Unit, ReplyError.DeleteReply> =
        try {
            val response = client.post {
                endPoint("deleteReply")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val message = response.body<ReplyError.DeleteReply>()
                error(message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(ReplyError.DeleteReply.SERVER_ERROR)
        }


    override suspend fun upvoteReply(request: ReplyRequest.UpvoteRequest): Result<Unit, ReplyError.UpvoteReply> =
        try {
            val response = client.post {
                endPoint("upvoteReply")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val serverError = response.body<ReplyError.UpvoteReply>()
                error(serverError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(ReplyError.UpvoteReply.SERVER_ERROR)
        }


    override suspend fun downvoteReply(request: ReplyRequest.DownvoteRequest): Result<Unit, ReplyError.DownvoteReply> =
        try {
            val response = client.post {
                endPoint("downvoteReply")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val serverError = response.body<ReplyError.DownvoteReply>()
                error(serverError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(ReplyError.DownvoteReply.SERVER_ERROR)
        }


    override suspend fun reportReply(request: ReplyRequest.ReportRequest): Result<Unit, ReplyError.ReportReply> =
        try {
            val response = client.post {
                endPoint("reportReply")
                setBody(
                    request
                )
            }
            val message = response.bodyAsText()
            if (response.status == HttpStatusCode.OK) {
                success(Unit)
            } else {
                val serverError = response.body<ReplyError.ReportReply>()
                error(serverError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error(ReplyError.ReportReply.SERVER_ERROR)
        }

}