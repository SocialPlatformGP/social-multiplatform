package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.data.post.source.remote.model.ReplyRequest
import com.gp.socialapp.data.post.util.endPoint
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
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

    override suspend fun createReply(request: ReplyRequest.CreateRequest): Results<Unit, DataError.Network> =
        try {
            val response = client.post {
                endPoint("createReply")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Results.Success(Unit)
            } else {
                val error = response.body<DataError.Network>()
                Results.Failure(error)

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }


    override fun fetchReplies(request: ReplyRequest.FetchRequest): Flow<Results<List<Reply>, DataError.Network>> =
        flow {
            emit(Results.Loading)
            println("fetchReplies: $request")
            try {
                val response = client.post {
                    endPoint("fetchReplies")
                    setBody(
                        request
                    )
                }
                if (response.status == HttpStatusCode.OK) {
                    val replies = response.body<List<Reply>>()
                    emit(Results.Success(replies))
                } else {
                    val error = response.body<DataError.Network>()
                    emit(Results.Failure(error))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
            }
        }

    override suspend fun updateReply(request: ReplyRequest.UpdateRequest): Results<Unit, DataError.Network> =
        try {
            println("updateReply request: $request")
            val response = client.post {
                endPoint("updateReply")
                setBody(
                    request
                )
            }
            println("updateReply response: ${response.status}")
            if (response.status == HttpStatusCode.OK) {
                Results.Success(Unit)
            } else {
                val error = response.body<DataError.Network>()
                Results.Failure(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }


    override suspend fun deleteReply(request: ReplyRequest.DeleteRequest): Results<Unit, DataError.Network> =
        try {
            val response = client.post {
                endPoint("deleteReply")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Results.Success(Unit)
            } else {
                val message = response.body<DataError.Network>()
                Results.Failure(message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }


    override suspend fun upvoteReply(request: ReplyRequest.UpvoteRequest): Results<Unit, DataError.Network> =
        try {
            val response = client.post {
                endPoint("upvoteReply")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Results.Success(Unit)
            } else {
                val error = response.body<DataError.Network>()
                Results.Failure(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }


    override suspend fun downvoteReply(request: ReplyRequest.DownvoteRequest): Results<Unit, DataError.Network> =
        try {
            val response = client.post {
                endPoint("downvoteReply")
                setBody(
                    request
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Results.Success(Unit)
            } else {
                val error = response.body<DataError.Network>()
                Results.Failure(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }


    override suspend fun reportReply(request: ReplyRequest.ReportRequest): Results<Unit, DataError.Network> =
        try {
            val response = client.post {
                endPoint("reportReply")
                setBody(
                    request
                )
            }
            val message = response.bodyAsText()
            if (response.status == HttpStatusCode.OK) {
                Results.Success(Unit)
            } else {
                val error = response.body<DataError.Network>()
                Results.Failure(error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN)
        }

}