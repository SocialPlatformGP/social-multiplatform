package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface ReplyRepository {
    fun getReplies(postId: String): Flow<Result<List<Reply>>>
    suspend fun updateReply(reply: Reply): Result<Nothing>
    suspend fun deleteReply(replyId: String): Result<Nothing>
    suspend fun upvoteReply(replyId: String, currentUserId: String): Result<Nothing>
    suspend fun downvoteReply(replyId: String, currentUserId: String): Result<Nothing>
    suspend fun insertReply(reply: Reply): Result<Nothing>
    suspend fun reportReply(replyId: String, reporterId: String): Result<Nothing>
//    suspend fun getReplyCountByPostId(postId: String): Result<Int>
}