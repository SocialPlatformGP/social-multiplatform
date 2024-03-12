package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.local.ReplyLocalDataSource
import com.gp.socialapp.data.post.source.remote.ReplyRemoteDataSource
import com.gp.socialapp.data.post.source.remote.model.Reply
import kotlinx.coroutines.flow.Flow

class ReplyRepositoryImpl(
    private val replyLocalDataSource: ReplyLocalDataSource,
    private val replyRemoteDataSource: ReplyRemoteDataSource,
    private val postRepository: PostRepository

): ReplyRepository{
    override fun getReplies(postId: String): Flow<List<Reply>> {
        deleteAllReplies()
//        return if (networkStatus.isOnline()) {
//            val replies = replyRemoteDataSource.fetchReplies(postId)
//            repositoryScope.launch {
//                replies.collect {
//                    replyLocalDataSource.insertReplies(it)
//                }
//            }
//            replies
//        } else {
//            replyLocalDataSource.getRepliesByPostId(postId).toReplyFlow()
//        }
        return replyLocalDataSource.getRepliesByPostId(postId)
    }

    override suspend fun insertReply(reply: Reply) {
//        if (networkStatus.isOnline()) {
//            replyRemoteDataSource.createReply(reply)
//            postRepository.incrementReplyCounter(reply.postId)
//        }
        //todo return state errror  in else
    }

    override suspend fun getReplyCountByPostId(postId: String): Int =
        replyRemoteDataSource.getReplyCountByPostId(postId)

    override suspend fun insertReplies(replies: List<Reply>) =
        replyLocalDataSource.insertReplies(replies)

    override suspend fun updateReply(reply: Reply) {
//        if (networkStatus.isOnline()) {
//            replyRemoteDataSource.updateReplyRemote(reply)
//            replyLocalDataSource.updateReply(reply)
//        }
        //todo return state errror  in else

    }
    override suspend fun upVoteReply(reply: Reply) {
//        if (networkStatus.isOnline()) {
//            replyRemoteDataSource.upVoteReply(reply)
//            replyLocalDataSource.upVoteLocal(reply.id)
//        } else {
//            //todo return state errror  in else
//        }

    }

    override suspend fun downVoteReply(reply: Reply) {
//        if (networkStatus.isOnline()) {
//            replyRemoteDataSource.downVoteReply(reply)
//            replyLocalDataSource.downVoteLocal(reply.id)
//        } else {
//            //todo return state errror  in else
//        }
    }

    override suspend fun deleteReply(reply: Reply) {
//        if (networkStatus.isOnline()) {
//            replyRemoteDataSource.updateReplyRemote(reply)
//            replyLocalDataSource.updateReply(reply)
//        } else {
//            //todo return state errror  in else
//        }
    }

    override fun deleteAllReplies() {
        replyLocalDataSource.deleteAllReplies()
    }
}