package com.gp.socialapp.data.post.source.local

import com.gp.socialapp.data.post.source.remote.model.Post
import kotlinx.coroutines.flow.Flow

interface PostLocalDataSource {
    suspend fun insertPost(post: Post)
    fun getAllPosts(): Flow<List<Post>>
    fun getPostById(id: String): Flow<Post>
    suspend fun deletePostById(id: String)
    suspend fun deleteAllPosts()
}