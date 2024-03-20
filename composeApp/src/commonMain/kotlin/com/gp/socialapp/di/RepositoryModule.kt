package com.gp.socialapp.di

import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.AuthenticationRepositoryImpl
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.repository.UserRepositoryImpl
import com.gp.socialapp.data.post.repository.PostRepository
import com.gp.socialapp.data.post.repository.PostRepositoryImpl
import com.gp.socialapp.data.post.repository.ReplyRepository
import com.gp.socialapp.data.post.source.remote.model.Reply
import kotlinx.coroutines.flow.Flow
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


val repositoryModuleK = DI.Module("repositoryModule") {
    bind<UserRepository>() with singleton { UserRepositoryImpl(instance()) }
    bind<AuthenticationRepository>() with singleton {
        AuthenticationRepositoryImpl(
            instance(),
            instance()
        )
    }
    bind<PostRepository>() with singleton { PostRepositoryImpl(instance(), instance(), instance(), instance()) }
    bind<ReplyRepository>() with singleton {
        object : ReplyRepository {
            override fun getReplies(postId: String): Flow<List<Reply>> {
                TODO("Not yet implemented")
            }

            override suspend fun insertReplies(replies: List<Reply>) {
                TODO("Not yet implemented")
            }

            override suspend fun updateReply(replyEntity: Reply) {
                TODO("Not yet implemented")
            }

            override suspend fun deleteReply(replyEntity: Reply) {
                TODO("Not yet implemented")
            }

            override fun deleteAllReplies() {
                TODO("Not yet implemented")
            }

            override suspend fun upVoteReply(reply: Reply) {
                TODO("Not yet implemented")
            }

            override suspend fun downVoteReply(reply: Reply) {
                TODO("Not yet implemented")
            }

            override suspend fun insertReply(reply: Reply) {
                TODO("Not yet implemented")
            }

            override suspend fun getReplyCountByPostId(postId: String): Int {
                TODO("Not yet implemented")
            }

        }
    }
}
