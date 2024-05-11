package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.util.ReplyError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface ReplyRepository {
    fun getReplies(postId: String): Flow<Result<List<Reply>, ReplyError>>
    suspend fun updateReply(replyId: String, replyContent: String): Result<Unit, ReplyError>
    suspend fun deleteReply(replyId: String): Result<Unit, ReplyError>
    suspend fun upvoteReply(
        replyId: String,
        currentUserId: String
    ): Result<Unit, ReplyError>

    suspend fun downvoteReply(
        replyId: String,
        currentUserId: String
    ): Result<Unit, ReplyError>

    suspend fun insertReply(reply: Reply): Result<Unit, ReplyError>
    suspend fun reportReply(replyId: String, reporterId: String): Result<Unit, ReplyError>
}