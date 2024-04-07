package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorage
import com.gp.socialapp.data.post.source.remote.ReplyRemoteDataSource
import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.data.post.source.remote.model.ReplyRequest
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class ReplyRepositoryImpl(
    private val remoteSource: ReplyRemoteDataSource,
    private val authStorage: AuthKeyValueStorage
): ReplyRepository {
    override fun getReplies(postId: String): Flow<Result<List<Reply>>> {
        val request = ReplyRequest.FetchRequest(postId)
        return remoteSource.fetchReplies(request)
    }

    override suspend fun updateReply(reply: Reply): Result<Nothing>{
        val request = ReplyRequest.UpdateRequest(reply)
        return remoteSource.updateReply(request)
    }

    override suspend fun deleteReply(replyId: String): Result<Nothing>{
        val request = ReplyRequest.DeleteRequest(replyId)
        return remoteSource.deleteReply(request)
    }

    override suspend fun upvoteReply(replyId: String, currentUserId: String): Result<Nothing>{
        val request = ReplyRequest.UpvoteRequest(replyId, currentUserId)
        return remoteSource.upvoteReply(request)
    }

    override suspend fun downvoteReply(replyId: String, currentUserId: String): Result<Nothing>{
        val request = ReplyRequest.DownvoteRequest(replyId, currentUserId)
        return remoteSource.downvoteReply(request)
    }

    override suspend fun insertReply(reply: Reply): Result<Nothing>{
        val request = ReplyRequest.CreateRequest(reply)
        return remoteSource.createReply(request)
    }

    override suspend fun reportReply(replyId: String, reporterId: String): Result<Nothing> {
        val request = ReplyRequest.ReportRequest(replyId, reporterId)
        return remoteSource.reportReply(request)
    }

//    override suspend fun getReplyCountByPostId(postId: String): Result<Int> {
//        return remoteSource.getReplyCountByPostId(postId)
//    }
}