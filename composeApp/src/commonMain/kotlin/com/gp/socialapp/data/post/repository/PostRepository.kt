package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun insertLocalPost(post: Post)
    fun getPosts(): Flow<Results<List<Post>, DataError.Network>>
    suspend fun updatePost(post: Post): Results<Unit, DataError.Network>
    suspend fun deletePost(post: Post): Results<Unit, DataError.Network>
    suspend fun upvotePost(post: Post, userId: String): Results<Unit, DataError.Network>
    suspend fun downvotePost(post: Post, userId: String): Results<Unit, DataError.Network>
    suspend fun fetchPostById(id: String): Flow<Post>
    suspend fun createPost(post: Post): Flow<Results<Unit, DataError.Network>>
    suspend fun reportPost(postId: String, reporterId: String): Results<Unit, DataError.Network>
    fun searchByTitle(title: String): Flow<Results<List<Post>, DataError.Network>>
    fun getAllTags(communityId: String): Flow<List<Tag>>
    suspend fun insertTag(tag: Tag)
    suspend fun getRecentSearches(): List<String>
    suspend fun deleteRecentSearch(search: String)
    suspend fun addRecentSearch(search: String)
    fun searchByTag(tag: Tag): Flow<Results<List<Post>, DataError.Network>>
    suspend fun openAttachment(url: String, mimeType: String)
}