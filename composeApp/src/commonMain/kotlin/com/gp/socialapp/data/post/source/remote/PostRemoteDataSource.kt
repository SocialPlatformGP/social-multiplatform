package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostRequest.DeleteRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.DownvoteRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.FetchRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.UpvoteRequest
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostRemoteDataSource {
    suspend fun createPost(post: Post): Flow<Result<String>>
    fun fetchPosts(request: FetchRequest): Flow<Result<List<Post>>>
    suspend fun updatePost(post: Post): Result<Nothing>
    suspend fun deletePost(request: DeleteRequest): Result<Nothing>
    suspend fun upvotePost(request: UpvoteRequest): Result<Nothing>
    suspend fun downvotePost(request: DownvoteRequest): Result<Nothing>

    fun getAllTags(): Flow<List<Tag>>
    suspend fun insertTag(tag: Tag)
    fun fetchAllPosts(): Flow<Result<List<Post>>>
}