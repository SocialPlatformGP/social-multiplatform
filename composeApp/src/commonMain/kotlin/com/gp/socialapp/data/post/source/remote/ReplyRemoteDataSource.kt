package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.data.post.source.remote.model.ReplyRequest
import com.gp.socialapp.util.ReplyError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface ReplyRemoteDataSource {
    suspend fun createReply(request: ReplyRequest.CreateRequest): Result<Unit, ReplyError.InsertReply>
    fun fetchReplies(request: ReplyRequest.FetchRequest): Flow<Result<List<Reply>, ReplyError.GetReplies>>
    suspend fun updateReply(request: ReplyRequest.UpdateRequest): Result<Unit, ReplyError.UpdateReply>
    suspend fun deleteReply(request: ReplyRequest.DeleteRequest): Result<Unit, ReplyError.DeleteReply>
    suspend fun upvoteReply(request: ReplyRequest.UpvoteRequest): Result<Unit, ReplyError.UpvoteReply>
    suspend fun downvoteReply(request: ReplyRequest.DownvoteRequest): Result<Unit, ReplyError.DownvoteReply>
    suspend fun reportReply(request: ReplyRequest.ReportRequest): Result<Unit, ReplyError.ReportReply>
}