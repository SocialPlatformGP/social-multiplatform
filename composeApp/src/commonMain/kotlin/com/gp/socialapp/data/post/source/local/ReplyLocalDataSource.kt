package com.gp.socialapp.data.post.source.local

import com.gp.socialapp.data.post.source.remote.model.Reply
import kotlinx.coroutines.flow.Flow

interface ReplyLocalDataSource {
    suspend fun insertReply(replyEntity: Reply): Long
    suspend fun insertReplies(replies: List<Reply>)
    suspend fun updateReply(replyEntity: Reply)
    suspend fun updateReplies(replies: List<Reply>)
    suspend fun deleteReply(replyEntity: Reply)
    suspend fun deleteReplies(replies: List<Reply>)
    fun deleteAllReplies()
    fun getAllReplies(): Flow<List<Reply>>
    fun getRepliesByPostId(postId: String): Flow<List<Reply>>
    fun getReplyById(id: Long): Flow<Reply>
    fun getRepliesByParentReplyId(parentReplyId: Long): Flow<List<Reply>>
    fun getTopLevelRepliesByPostId(postId: String): Flow<List<Reply>>
    suspend fun upVoteLocal(id:String)
    suspend  fun downVoteLocal(id:String)
}