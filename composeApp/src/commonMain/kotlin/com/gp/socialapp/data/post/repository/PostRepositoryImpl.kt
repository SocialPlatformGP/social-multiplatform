package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.data.post.source.remote.PostRemoteDataSource
import com.gp.socialapp.data.post.source.remote.model.FetchPostsRequest
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.AppConstants
import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.Platform
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.getPlatform
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class PostRepositoryImpl(
    private val postLocalSource: PostLocalDataSource,
    private val postRemoteSource: PostRemoteDataSource,
    private val settings: Settings
) : PostRepository {

    private var lastUpdated: Long
        get() = settings.getLong(AppConstants.StorageKeys.POST_LAST_UPDATED.key, 0L)
        set(value) {
            settings[AppConstants.StorageKeys.POST_LAST_UPDATED.key] = value
        }

    override suspend fun createPost(post: Post): Flow<Result<String>> {
        return postRemoteSource.createPost(post)
    }

    override suspend fun insertLocalPost(post: Post) {
        postLocalSource.insertPost(post)
    }

    override fun getAllPosts(): Flow<Result<List<Post>>> = flow {
        emit(Result.Loading)
        val platform = getPlatform()
        try{
            if (platform == Platform.JS) {
                getRemotePosts().collect {
                    emit(it)
                }
            } else {
                flow {
                    getRemotePosts().collect {
                        if(it is Result.SuccessWithData && it.data.isNotEmpty()) {
                            lastUpdated =
                                LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds
                            it.data.forEach { post ->
                                insertLocalPost(post)
                            }
                        }
                    }
                    getAllLocalPosts().collect {
                        emit(it)
                    }
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message?: "An error occurred"))
        }
    }

    override fun getRemotePosts(): Flow<Result<List<Post>>> {
        return postRemoteSource.fetchPosts(
            FetchPostsRequest(
                lastUpdated = lastUpdated
            )
        )
    }

    override fun getAllLocalPosts(): Flow<List<Post>> {
        return postLocalSource.getAllPosts()
    }

    override suspend fun deleteLocalPostById(id: String) {
        postLocalSource.deletePostById(id)
    }


    override suspend fun updatePost(post: Post): Flow<Result<String>> =
        postRemoteSource.updatePost(post)


    override suspend fun deletePost(post: Post) {
        postRemoteSource.deletePost(post)
    }


    override fun onCleared() {
//        repositoryScope.cancel()
    }


    override suspend fun upVotePost(post: Post) = postRemoteSource.upVotePost(post)

    override suspend fun downVotePost(post: Post) = postRemoteSource.downVotePost(post)
    override suspend fun fetchPostById(id: String): Flow<Post> {
        return postLocalSource.getPostById(id)
    }

    override fun deleteAllPosts() {

    }


    override suspend fun incrementReplyCounter(postId: String) =
        postRemoteSource.incrementReplyCounter(postId)

    override suspend fun decrementReplyCounter(postId: String) =
        postRemoteSource.decrementReplyCounter(postId)

    override fun getAllTags() = postRemoteSource.getAllTags()

    override suspend fun insertTag(tag: Tag) = postRemoteSource.insertTag(tag)
}