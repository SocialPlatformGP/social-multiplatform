package com.gp.socialapp.di

import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorage
import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorageImpl
import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.data.post.source.local.ReplyLocalDataSource
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Reply
import kotlinx.coroutines.flow.Flow
import org.koin.dsl.module

val localSourceModule = module {
    single<AuthKeyValueStorage> { AuthKeyValueStorageImpl() }
    single<PostLocalDataSource> {
        object : PostLocalDataSource {
            override suspend fun insertPost(vararg post: Post) {
                TODO("Not yet implemented")
            }

            override suspend fun updatePost(post: Post) {
                TODO("Not yet implemented")
            }

            override fun getAllPosts(): Flow<List<Post>> {
                TODO("Not yet implemented")
            }

            override suspend fun deletePost(post: Post) {
                TODO("Not yet implemented")
            }

            override fun searchPostsByTitle(searchText: String): Flow<List<Post>> {
                TODO("Not yet implemented")
            }

            override fun getPostById(id: String): Flow<Post> {
                TODO("Not yet implemented")
            }

            override fun deleteAllPosts() {
                TODO("Not yet implemented")
            }

        }
    }
    single<ReplyLocalDataSource>{object: ReplyLocalDataSource{
        override suspend fun insertReply(replyEntity: Reply): Long {
            TODO("Not yet implemented")
        }

        override suspend fun insertReplies(replies: List<Reply>) {
            TODO("Not yet implemented")
        }

        override suspend fun updateReply(replyEntity: Reply) {
            TODO("Not yet implemented")
        }

        override suspend fun updateReplies(replies: List<Reply>) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteReply(replyEntity: Reply) {
            TODO("Not yet implemented")
        }

        override suspend fun deleteReplies(replies: List<Reply>) {
            TODO("Not yet implemented")
        }

        override fun deleteAllReplies() {
            TODO("Not yet implemented")
        }

        override fun getAllReplies(): Flow<List<Reply>> {
            TODO("Not yet implemented")
        }

        override fun getRepliesByPostId(postId: String): Flow<List<Reply>> {
            TODO("Not yet implemented")
        }

        override fun getReplyById(id: Long): Flow<Reply> {
            TODO("Not yet implemented")
        }

        override fun getRepliesByParentReplyId(parentReplyId: Long): Flow<List<Reply>> {
            TODO("Not yet implemented")
        }

        override fun getTopLevelRepliesByPostId(postId: String): Flow<List<Reply>> {
            TODO("Not yet implemented")
        }

        override suspend fun upVoteLocal(id: String) {
            TODO("Not yet implemented")
        }

        override suspend fun downVoteLocal(id: String) {
            TODO("Not yet implemented")
        }

    }}
}