package com.gp.socialapp.data.post.repository

import com.apollographql.apollo3.mpp.platform
import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorage
import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.data.post.source.remote.PostRemoteDataSource
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostRequest
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
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class PostRepositoryImpl(
    private val postLocalSource: PostLocalDataSource,
    private val postRemoteSource: PostRemoteDataSource,
    private val authStorage: AuthKeyValueStorage,
    private val settings: Settings
) : PostRepository {

    private var lastUpdated: Long
        get() = settings.getLong(AppConstants.StorageKeys.POST_LAST_UPDATED.key, 0L)
        set(value) {
            settings[AppConstants.StorageKeys.POST_LAST_UPDATED.key] = value
        }

    override suspend fun createPost(post: Post): Flow<Result<String>> {
        val request = PostRequest.CreateRequest(post)
        println("request: $request")
        return postRemoteSource.createPost(request)
    }

    override suspend fun reportPost(postId: String, reporterId: String): Result<Nothing> {
        val request = PostRequest.ReportRequest(postId, reporterId)
        return postRemoteSource.reportPost(request)
    }

    override suspend fun searchByTitle(title: String): Flow<Result<List<Post>>> = flow {
        emit(Result.Loading)
        val platform = getPlatform()
        try {
            if(title.isEmpty()) {
                emit(Result.SuccessWithData(emptyList()))
                return@flow
            } else if(platform == Platform.JS) {
                postRemoteSource.searchByTitle(title).collect {
                    emit(it)
                }
                return@flow
            } else {
                postLocalSource.searchByTitle(title).collect {
                    emit(Result.SuccessWithData(it))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
        }
    }

    override suspend fun insertLocalPost(post: Post) {
        postLocalSource.insertPost(post)
    }

    override fun getPosts(): Flow<Result<List<Post>>> = flow {
        println("getPosts: $lastUpdated repo *********************125")
        emit(Result.Loading)
        val platform = getPlatform()
        println("platform: $platform")
        try {
            if (platform == Platform.JS) {
                println("getPosts: $lastUpdated repo *********************126")
                postRemoteSource.fetchAllPosts().collect {
                    emit(it)
                }
            } else {
                println("getPosts: $lastUpdated repo *********************126")

                getRemotePosts().collect {
                    if (it is Result.SuccessWithData && it.data.isNotEmpty()) {
                        println("getPosts: $lastUpdated repo *********************127")
                        lastUpdated =
                            LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds
                        it.data.forEach { post ->
                            insertLocalPost(post)
                        }
                    }
                }
                getLocalPosts().collect {
                    emit(Result.SuccessWithData(it))
                }

            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
        }
    }

    private fun getRemotePosts(): Flow<Result<List<Post>>> {
        println("getRemotePosts: $lastUpdated repo *********************1255")
        return postRemoteSource.fetchPosts(
            PostRequest.FetchRequest(
                lastUpdated = lastUpdated
            )
        )
    }

    private fun getLocalPosts(): Flow<List<Post>> {
        return postLocalSource.getAllPosts()
    }

    override suspend fun updatePost(post: Post): Result<Nothing> {
        val request = PostRequest.UpdateRequest(post)
        return postRemoteSource.updatePost(request)
    }


    override suspend fun deletePost(post: Post): Result<Nothing> {
        val request = PostRequest.DeleteRequest(post.id)
        postLocalSource.deletePostById(post.id)
        return postRemoteSource.deletePost(request)
    }

    override suspend fun upvotePost(post: Post): Result<Nothing> {
        val request = PostRequest.UpvoteRequest(
            post.id,
            authStorage.userId ?: "25"
        )
        return postRemoteSource.upvotePost(request)
    }

    override suspend fun downvotePost(post: Post): Result<Nothing> {
        val request = PostRequest.DownvoteRequest(
            post.id,
            authStorage.userId ?: "25"
        )
        return postRemoteSource.downvotePost(request)
    }

    override suspend fun fetchPostById(id: String): Flow<Post> {
        return postLocalSource.getPostById(id)
    }

    override fun getAllTags() = postRemoteSource.getAllTags()

    override suspend fun insertTag(tag: Tag) = postRemoteSource.insertTag(tag)
}