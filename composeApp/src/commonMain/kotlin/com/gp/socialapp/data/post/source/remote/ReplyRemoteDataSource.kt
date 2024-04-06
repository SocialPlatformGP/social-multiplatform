package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.PostRequest
import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.data.post.source.remote.model.ReplyRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface ReplyRemoteDataSource {
    suspend fun createReply(request: ReplyRequest.CreateRequest): Result<Nothing>
    fun fetchReplies(request: ReplyRequest.FetchRequest): Flow<Result<List<Reply>>>
    suspend fun updateReply(request: ReplyRequest.UpdateRequest): Result<Nothing>
    suspend fun deleteReply(request: ReplyRequest.DeleteRequest): Result<Nothing>

    suspend fun upvoteReply(request: ReplyRequest.UpvoteRequest): Result<Nothing>
    suspend fun downvoteReply(request: ReplyRequest.DownvoteRequest): Result<Nothing>
//    suspend fun getReplyCountByPostId(postId: String): Result<Int>
}