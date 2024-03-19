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
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class PostRepositoryImpl(
    private val postLocalSource: PostLocalDataSource,
    private val postRemoteSource: PostRemoteDataSource,
    private val settings: Settings
) : PostRepository {

    private var lastUpdated: Int
        get() = settings.getInt(AppConstants.StorageKeys.POST_LAST_UPDATED.key, 0)
        set(value) {
            settings[AppConstants.StorageKeys.POST_LAST_UPDATED.key] = value
        }

    override suspend fun createPost(post: Post): Flow<Result<String>> {
        return postRemoteSource.createPost(post)
    }

    override suspend fun insertLocalPost(post: Post) {
        postLocalSource.insertPost(post)
    }

    override fun getAllPosts(scope: CoroutineScope): Flow<List<Post>> = flow {
        val platform = getPlatform()
        scope.launch {
            while (isActive) {
                try {
                    if (platform == Platform.JS) {
                        getRemotePosts().collect {
                            if (it.isNotEmpty()) {
                                emit(it)
                            }
                        }
                    } else {
                        launch {
                            val posts = postRemoteSource.fetchPosts(
                                FetchPostsRequest(
                                    Instant.fromEpochSeconds(lastUpdated.toLong()).toLocalDateTime(
                                        TimeZone.UTC
                                    )
                                )
                            )
                            posts.collect {
                                lastUpdated = LocalDateTime.now().second
                                it.forEach { post ->
                                    insertLocalPost(post)
                                }
                            }
                            getAllLocalPosts().collect {
                                emit(it)
                            }
                        }
                    }
                    delay(60000)
                } catch (e: Exception) {
                    Napier.e(
                        message = e.message ?: "Error in getAllPosts and null exception message"
                    )
                }

            }
        }
    }

    override fun getRemotePosts(): Flow<List<Post>> {
        return postRemoteSource.fetchPosts(
            FetchPostsRequest(
                Instant.fromEpochSeconds(lastUpdated.toLong()).toLocalDateTime(
                    TimeZone.UTC
                )
            )
        )
    }

    override fun getAllLocalPosts(): Flow<List<Post>> {
//        if (networkStatus.isOnline()) {
//            val posts = postRemoteSource.fetchPosts()
//            repositoryScope.launch {
//                postLocalSource.deleteAllPosts()
//                posts.collect {
//                    it.forEach { post ->
//                        insertLocalPost(post.toEntity())
//                    }
//                }
//            }
//            return posts
//        } else {
//            return postLocalSource.getAllPosts().toPostFlow()
//        }
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

//    override fun createPost(post: Post, files: List<PostFile>): Flow<State<Nothing>> {
//        return if(files.isEmpty()) {
//            postRemoteSource.createPost(post)
//        } else {
//            postRemoteSource.createPostWithFiles(post, files)
//        }
//    }

    override fun onCleared() {
//        repositoryScope.cancel()
    }


    override suspend fun upVotePost(post: Post) = postRemoteSource.upVotePost(post)

    override suspend fun downVotePost(post: Post) = postRemoteSource.downVotePost(post)
    override fun fetchPostById(id: String): Flow<Post> {
//        if (networkStatus.isOnline()) {
//            val post = postRemoteSource.fetchPostById(id)
//            repositoryScope.launch {
//                post.collect {
//                    insertLocalPost(it.toEntity())
//                }
//            }
//            return post
//        }
//        else{
//            return postLocalSource.getPostById(id).toModel()
//        }
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