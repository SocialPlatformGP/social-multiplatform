package com.gp.socialapp.di

import com.eygraber.uri.Uri
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.AuthenticationRemoteDataSourceImpl
import com.gp.socialapp.data.auth.source.remote.UserRemoteDataSource
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.post.source.remote.PostRemoteDataSource
import com.gp.socialapp.data.post.source.remote.PostRemoteDataSourceImpl
import com.gp.socialapp.data.post.source.remote.ReplyRemoteDataSource
import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton


val remoteDataSourceModuleK = DI.Module("remoteDataSourceModule") {
    bind<PostRemoteDataSource>() with singleton { PostRemoteDataSourceImpl() }
    bind<ReplyRemoteDataSource>() with singleton {
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
    bind<AuthenticationRemoteDataSource>() with singleton { AuthenticationRemoteDataSourceImpl() }
    bind<UserRemoteDataSource>() with singleton {
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

}
