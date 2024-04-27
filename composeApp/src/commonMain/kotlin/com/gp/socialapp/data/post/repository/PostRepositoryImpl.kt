package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.auth.source.local.AuthKeyValueStorage
import com.gp.socialapp.data.material.source.remote.MaterialRemoteDataSource
import com.gp.socialapp.data.material.utils.FileManager
import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.data.post.source.remote.PostRemoteDataSource
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostRequest
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.AppConstants
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.Platform
import com.gp.socialapp.util.Results
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
    private val materialRemoteDataSource: MaterialRemoteDataSource,
    private val fileManager: FileManager,
    private val settings: Settings
) : PostRepository {
    private var lastUpdated: Long
        get() = settings.getLong(AppConstants.StorageKeys.POST_LAST_UPDATED.key, 0L)
        set(value) {
            settings[AppConstants.StorageKeys.POST_LAST_UPDATED.key] = value
        }
    private var recentSearches: String
        get() = settings.getString(AppConstants.StorageKeys.RECENT_SEARCHES.key, "")
        set(value) {
            settings[AppConstants.StorageKeys.RECENT_SEARCHES.key] = value
        }

    override suspend fun createPost(post: Post): Flow<Results<Unit, DataError.Network>> {
        val request = PostRequest.CreateRequest(post)
        return postRemoteSource.createPost(request)
    }

    override suspend fun reportPost(
        postId: String,
        reporterId: String
    ): Results<Unit, DataError.Network> {
        val request = PostRequest.ReportRequest(postId, reporterId)
        return postRemoteSource.reportPost(request)
    }

    override fun searchByTitle(title: String): Flow<Results<List<Post>, DataError.Network>> = flow {
        emit(Results.Loading)
        val platform = getPlatform()
        try {
            if (title.isEmpty()) {
                emit(Results.Success(emptyList()))
                return@flow
            } else if (platform == Platform.JS) {
                postRemoteSource.searchByTitle(title).collect {
                    emit(it)
                }
                return@flow
            } else {
                postLocalSource.searchByTitle(title).collect {
                    emit(Results.Success(it))
                }
            }
        } catch (e: Exception) {
            emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
        }
    }

    override suspend fun insertLocalPost(post: Post) {
        postLocalSource.insertPost(post)
    }

    override fun getPosts(): Flow<Results<List<Post>, DataError.Network>> = flow {
        println("getPosts: $lastUpdated repo *********************125")
        emit(Results.Loading)
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
                    if (it is Results.Success && it.data.isNotEmpty()) {
                        println("getPosts: $lastUpdated repo *********************127")
                        lastUpdated =
                            LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds
                        it.data.forEach { post ->
                            insertLocalPost(post)
                        }
                    }
                }
                getLocalPosts().collect {
                    emit(Results.Success(it))
                }

            }
        } catch (e: Exception) {
            emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
        }
    }

    private fun getRemotePosts(): Flow<Results<List<Post>, DataError.Network>> {
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

    override suspend fun updatePost(post: Post): Results<Unit, DataError.Network> {
        val request = PostRequest.UpdateRequest(post)
        return postRemoteSource.updatePost(request)
    }


    override suspend fun deletePost(post: Post): Results<Unit, DataError.Network> {
        val request = PostRequest.DeleteRequest(post.id)
        postLocalSource.deletePostById(post.id)
        return postRemoteSource.deletePost(request)
    }

    override suspend fun upvotePost(post: Post, userId: String): Results<Unit, DataError.Network> {
        val request = PostRequest.UpvoteRequest(
            post.id,
            userId
        )
        return postRemoteSource.upvotePost(request)
    }

    override suspend fun downvotePost(
        post: Post,
        userId: String
    ): Results<Unit, DataError.Network> {
        val request = PostRequest.DownvoteRequest(
            post.id,
            userId
        )
        return postRemoteSource.downvotePost(request)
    }

    override suspend fun fetchPostById(id: String): Flow<Post> {
        return postLocalSource.getPostById(id)
    }

    override fun getAllTags() = postRemoteSource.getAllTags()

    override suspend fun insertTag(tag: Tag) = postRemoteSource.insertTag(tag)
    override suspend fun getRecentSearches(): List<String> {
        return recentSearches.split("%69%").filter { it.isNotEmpty() }
    }

    override suspend fun deleteRecentSearch(search: String) {
        if (recentSearches.contains("%69%$search".toRegex())) {
            recentSearches = recentSearches.replace("%69%$search", "")
        } else if (recentSearches.contains("$search%69%".toRegex())) {
            recentSearches = recentSearches.replace("$search%69%", "")
        }
    }

    override suspend fun addRecentSearch(search: String) {
        println("recentSearches before: ${recentSearches.isBlank()}, search: $search")
        recentSearches += "%69%$search"
        println("recentSearches after: $recentSearches")
    }

    override fun searchByTag(tag: Tag): Flow<Results<List<Post>, DataError.Network>> = flow {
        emit(Results.Loading)
        val platform = getPlatform()
        try {
            if (tag.label.isEmpty()) {
                emit(Results.Success(emptyList()))
                return@flow
            } else if (platform == Platform.JS) {
                postRemoteSource.searchByTag(tag.label).collect {
                    emit(it)
                }
                return@flow
            } else {
                postLocalSource.searchByTag(tag.label).collect {
                    emit(Results.Success(it))
                }
            }
        } catch (e: Exception) {
            emit(Results.Failure(DataError.Network.NO_INTERNET_OR_SERVER_DOWN))
        }
    }

    override suspend fun openAttachment(url: String, mimeType: String) {
        try {
            when (val data = materialRemoteDataSource.downloadFile(url)) {
                is Results.Failure -> {
                    println(data.error)
                }

                Results.Loading -> {
                    //TODO
                }

                is Results.Success -> {
                    val localPath = fileManager.saveFile(data.data.data, data.data.fileName, mimeType)
                    fileManager.openFile(localPath, mimeType)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}