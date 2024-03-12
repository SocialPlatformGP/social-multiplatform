package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.Reply
import kotlinx.coroutines.flow.Flow

interface ReplyRemoteDataSource {
    suspend fun createReply(reply: Reply)
    fun fetchReplies(postId: String): Flow<List<Reply>>
    suspend fun updateReplyRemote(reply: Reply)
    suspend fun deleteReply(reply: Reply)

    suspend fun upVoteReply(reply: Reply)
    suspend fun downVoteReply(reply: Reply)
    suspend fun getReplyCountByPostId(postId: String):Int
}