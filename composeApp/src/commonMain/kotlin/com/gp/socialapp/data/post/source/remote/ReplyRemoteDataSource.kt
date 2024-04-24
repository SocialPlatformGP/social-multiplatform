package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.data.post.source.remote.model.ReplyRequest
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

interface ReplyRemoteDataSource {
    suspend fun createReply(request: ReplyRequest.CreateRequest): Results<Unit, DataError.Network>
    fun fetchReplies(request: ReplyRequest.FetchRequest): Flow<Results<List<Reply>, DataError.Network>>
    suspend fun updateReply(request: ReplyRequest.UpdateRequest): Results<Unit, DataError.Network>
    suspend fun deleteReply(request: ReplyRequest.DeleteRequest): Results<Unit, DataError.Network>
    suspend fun upvoteReply(request: ReplyRequest.UpvoteRequest): Results<Unit, DataError.Network>
    suspend fun downvoteReply(request: ReplyRequest.DownvoteRequest): Results<Unit, DataError.Network>
    suspend fun reportReply(request: ReplyRequest.ReportRequest): Results<Unit, DataError.Network>
}