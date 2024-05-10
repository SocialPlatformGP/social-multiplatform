package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.DeleteRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.DownvoteRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.FetchRequest
import com.gp.socialapp.data.post.source.remote.model.PostRequest.UpvoteRequest
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.PostError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostRemoteDataSource {
    suspend fun createPost(request: PostRequest.CreateRequest): Flow<Result<Unit, PostError.CreatePost>>
    fun fetchPosts(request: FetchRequest): Flow<Result<List<Post>,PostError.GetPosts>>
    suspend fun updatePost(request: PostRequest.UpdateRequest): Result<Unit, PostError.UpdatePost>
    suspend fun deletePost(request: DeleteRequest): Result<Unit, PostError.DeletePost>
    suspend fun upvotePost(request: UpvoteRequest): Result<Unit, PostError.UpvotePost>
    suspend fun downvotePost(request: DownvoteRequest): Result<Unit, PostError.DownvotePost>
    suspend fun reportPost(request: PostRequest.ReportRequest): Result<Unit, PostError.ReportPost>

    fun getAllTags(communityId: String): Flow<List<Tag>>
    suspend fun insertTag(tag: Tag)
    fun fetchAllPosts(): Flow<Result<List<Post>,PostError.GetPosts>>
    fun searchByTitle(title: String): Flow<Result<List<Post>, PostError.SearchByTitle>>
    fun searchByTag(tag: String): Flow<Result<List<Post>, PostError.SearchByTag>>
    suspend fun getUserPosts(userId: String): Result<List<Post>,PostError.GetUserPosts>
}