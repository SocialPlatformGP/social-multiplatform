package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.PostError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun insertLocalPost(post: Post)
    fun getPosts(): Flow<Result<List<Post>, PostError.GetPosts>>
    suspend fun updatePost(post: Post): Result<Unit, PostError.UpdatePost>
    suspend fun deletePost(post: Post): Result<Unit, PostError.DeletePost>
    suspend fun upvotePost(post: Post, userId: String): Result<Unit, PostError.UpvotePost>
    suspend fun downvotePost(post: Post, userId: String): Result<Unit, PostError.DownvotePost>
    suspend fun fetchPostById(id: String): Flow<Post>
    suspend fun createPost(post: Post): Flow<Result<Unit, PostError.CreatePost>>
    suspend fun reportPost(postId: String, reporterId: String): Result<Unit, PostError.ReportPost>
    fun searchByTitle(title: String): Flow<Result<List<Post>, PostError.SearchByTitle>>
    fun getAllTags(communityId: String): Flow<List<Tag>>
    suspend fun insertTag(tag: Tag)
    suspend fun getRecentSearches(): List<String>
    suspend fun deleteRecentSearch(search: String)
    suspend fun addRecentSearch(search: String)
    fun searchByTag(tag: Tag): Flow<Result<List<Post>, PostError.SearchByTag>>
    suspend fun openAttachment(url: String, mimeType: String)
    suspend fun getUserPosts(userId: String): Result<List<Post>,PostError.GetUserPosts>
}