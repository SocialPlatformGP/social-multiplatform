package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun insertLocalPost(post: Post)
    fun getPosts(): Flow<Result<List<Post>>>
    suspend fun updatePost(post: Post): Flow<Result<String>>
    suspend fun deletePost(post: Post): Result<Nothing>
    suspend fun upvotePost(post: Post): Result<Nothing>
    suspend fun downvotePost(post: Post): Result<Nothing>
    suspend fun fetchPostById(id: String): Flow<Post>
    suspend fun createPost(post: Post): Flow<Result<String>>

    fun getAllTags(): Flow<List<Tag>>
    suspend fun insertTag(tag: Tag)
}