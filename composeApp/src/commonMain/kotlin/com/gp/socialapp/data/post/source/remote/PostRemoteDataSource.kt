package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.DeleteRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.DownvoteRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.FetchRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.UpvoteRequest
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.DataError
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.Flow

interface PostRemoteDataSource {
    suspend fun createPost(request: PostRequest.CreateRequest): Flow<Results<Unit, DataError.Network>>
    fun fetchPosts(request: FetchRequest): Flow<Results<List<Post>, DataError.Network>>
    suspend fun updatePost(request: PostRequest.UpdateRequest): Results<Unit, DataError.Network>
    suspend fun deletePost(request: DeleteRequest): Results<Unit, DataError.Network>
    suspend fun upvotePost(request: UpvoteRequest): Results<Unit, DataError.Network>
    suspend fun downvotePost(request: DownvoteRequest): Results<Unit, DataError.Network>
    suspend fun reportPost(request: PostRequest.ReportRequest): Results<Unit, DataError.Network>

    fun getAllTags(): Flow<List<Tag>>
    suspend fun insertTag(tag: Tag)
    fun fetchAllPosts(): Flow<Results<List<Post>, DataError.Network>>
    fun searchByTitle(title: String): Flow<Results<List<Post>, DataError.Network>>
    fun searchByTag(tag: String): Flow<Results<List<Post>, DataError.Network>>
}