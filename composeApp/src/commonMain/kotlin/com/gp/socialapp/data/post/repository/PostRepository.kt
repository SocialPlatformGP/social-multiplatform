package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun insertLocalPost(post: Post)
    fun getPosts(): Flow<Result<List<Post>>>
    suspend fun updatePost(post: Post): Result<Nothing>
    suspend fun deletePost(post: Post): Result<Nothing>
    suspend fun upvotePost(post: Post): Result<Nothing>
    suspend fun downvotePost(post: Post): Result<Nothing>
    suspend fun fetchPostById(id: String): Flow<Post>
    suspend fun createPost(post: Post): Flow<Result<String>>
    suspend fun reportPost(postId: String, reporterId: String): Result<Nothing>
    suspend fun searchByTitle(title: String): Flow<Result<List<Post>>>
    fun getAllTags(): Flow<List<Tag>>
    suspend fun insertTag(tag: Tag)
    suspend fun getRecentSearches(): List<String>
    suspend fun deleteRecentSearch(search: String)
    suspend fun addRecentSearch(search: String)
}