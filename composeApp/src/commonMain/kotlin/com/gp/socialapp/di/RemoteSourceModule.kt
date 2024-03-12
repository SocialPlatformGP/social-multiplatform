package com.gp.socialapp.di

import com.eygraber.uri.Uri
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSourceImpl
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.post.source.remote.PostRemoteDataSource
import com.gp.socialapp.data.post.source.remote.ReplyRemoteDataSource
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.Result
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single<UserRemoteDataSource> {
        object : UserRemoteDataSource {
            override fun createUser(user: User, pfpURI: Uri): Flow<Result<Nothing>> {
                TODO("Not yet implemented")
            }

            override fun updateUser(user: User): Flow<Result<Nothing>> {
                TODO("Not yet implemented")
            }

            override fun deleteUser(user: User): Flow<Result<Nothing>> {
                TODO("Not yet implemented")
            }

            override suspend fun fetchUser(email: String): Result<User> {
                TODO("Not yet implemented")
            }

            override fun fetchUsers(): Flow<Result<List<User>>> {
                TODO("Not yet implemented")
            }

            override fun getCurrentUserEmail(): String {
                TODO("Not yet implemented")
            }

            override fun getUsersByEmails(emails: List<String>): Flow<Result<List<User>>> {
                TODO("Not yet implemented")
            }
        }
    }
    single<AuthenticationRemoteDataSource> { AuthenticationRemoteDataSourceImpl() }
    single<PostRemoteDataSource> {
        object : PostRemoteDataSource {
            override fun createPost(post: Post): Flow<Result<Nothing>> {
                TODO("Not yet implemented")
            }

            override fun fetchPosts(): Flow<List<Post>> {
                TODO("Not yet implemented")
            }

            override suspend fun updatePost(post: Post) {
                TODO("Not yet implemented")
            }

            override suspend fun deletePost(post: Post) {
                TODO("Not yet implemented")
            }

            override suspend fun upVotePost(post: Post) {
                TODO("Not yet implemented")
            }

            override suspend fun downVotePost(post: Post) {
                TODO("Not yet implemented")
            }

            override fun fetchPostById(id: String): Flow<Post> {
                TODO("Not yet implemented")
            }

            override suspend fun incrementReplyCounter(postId: String) {
                TODO("Not yet implemented")
            }

            override suspend fun decrementReplyCounter(postId: String) {
                TODO("Not yet implemented")
            }

            override fun getAllTags(): Flow<List<Tag>> {
                TODO("Not yet implemented")
            }

            override suspend fun insertTag(tag: Tag) {
                TODO("Not yet implemented")
            }

        }
    }
    single<ReplyRemoteDataSource> {
        object : ReplyRemoteDataSource {
            override suspend fun createReply(reply: Reply) {
                TODO("Not yet implemented")
            }

            override fun fetchReplies(postId: String): Flow<List<Reply>> {
                TODO("Not yet implemented")
            }

            override suspend fun updateReplyRemote(reply: Reply) {
                TODO("Not yet implemented")
            }

            override suspend fun deleteReply(reply: Reply) {
                TODO("Not yet implemented")
            }

            override suspend fun upVoteReply(reply: Reply) {
                TODO("Not yet implemented")
            }

            override suspend fun downVoteReply(reply: Reply) {
                TODO("Not yet implemented")
            }

            override suspend fun getReplyCountByPostId(postId: String): Int {
                TODO("Not yet implemented")
            }
        }
    }
}