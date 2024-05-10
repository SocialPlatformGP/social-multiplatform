package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.util.ReplyError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface ReplyRepository {
    fun getReplies(postId: String): Flow<Result<List<Reply>, ReplyError.GetReplies>>
    suspend fun updateReply(replyId: String, replyContent: String): Result<Unit, ReplyError.UpdateReply>
    suspend fun deleteReply(replyId: String): Result<Unit, ReplyError.DeleteReply>
    suspend fun upvoteReply(
        replyId: String,
        currentUserId: String
    ): Result<Unit, ReplyError.UpvoteReply>

    suspend fun downvoteReply(
        replyId: String,
        currentUserId: String
    ): Result<Unit, ReplyError.DownvoteReply>

    suspend fun insertReply(reply: Reply): Result<Unit, ReplyError.InsertReply>
    suspend fun reportReply(replyId: String, reporterId: String): Result<Unit, ReplyError.ReportReply>
}