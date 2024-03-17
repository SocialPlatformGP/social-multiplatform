package com.gp.socialapp.data.post.source.local

import com.gp.socialapp.data.post.source.remote.model.Post
import kotlinx.coroutines.flow.Flow

interface PostLocalDataSource {
    suspend fun insertPost(vararg post: Post)
    suspend fun updatePost(post: Post)
    fun getAllPosts(): Flow<List<Post>>
    suspend fun deletePost(post: Post)
    fun searchPostsByTitle(searchText: String): Flow<List<Post>>
    fun getPostById(id: String): Flow<Post>
    fun deleteAllPosts()
}