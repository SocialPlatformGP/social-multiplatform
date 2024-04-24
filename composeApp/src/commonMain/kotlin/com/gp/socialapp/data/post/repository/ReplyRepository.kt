package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

interface ReplyRepository {
    fun getReplies(postId: String): Flow<Results<List<Reply>, DataError.Network>>
    suspend fun updateReply(reply: Reply): Results<Unit, DataError.Network>
    suspend fun deleteReply(replyId: String): Results<Unit, DataError.Network>
    suspend fun upvoteReply(
        replyId: String,
        currentUserId: String
    ): Results<Unit, DataError.Network>

    suspend fun downvoteReply(
        replyId: String,
        currentUserId: String
    ): Results<Unit, DataError.Network>

    suspend fun insertReply(reply: Reply): Results<Unit, DataError.Network>
    suspend fun reportReply(replyId: String, reporterId: String): Results<Unit, DataError.Network>
}