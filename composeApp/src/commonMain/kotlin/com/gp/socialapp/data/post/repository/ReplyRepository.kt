package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.remote.model.Reply
import kotlinx.coroutines.flow.Flow

interface ReplyRepository {
    fun getReplies(postId: String): Flow<List<Reply>>
    suspend fun insertReplies(replies: List<Reply>)
    suspend fun updateReply(replyEntity: Reply)
    suspend fun deleteReply(replyEntity: Reply)
    fun deleteAllReplies()
    suspend fun upVoteReply(reply: Reply)
    suspend fun downVoteReply(reply: Reply)
    suspend fun insertReply(reply: Reply)
    suspend fun getReplyCountByPostId(postId: String):Int
}