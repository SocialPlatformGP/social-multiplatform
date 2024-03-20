package com.gp.socialapp.data.post.source.remote

import com.gp.socialapp.data.post.source.remote.model.FetchPostsRequest
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostRemoteDataSource {
    suspend fun createPost(post: Post): Flow<Result<String>>

    //    fun createPostWithFiles(post: Post, files: List<PostFile>): Flow<Result<Nothing>>
    fun fetchPosts(request: FetchPostsRequest): Flow<Result<List<Post>>>
    suspend fun updatePost(post: Post): Flow<Result<String>>
    suspend fun deletePost(post: Post)
    suspend fun upVotePost(post: Post)
    suspend fun downVotePost(post: Post)
    fun fetchPostById(id: String): Flow<Post>
    suspend fun incrementReplyCounter(postId: String)
    suspend fun decrementReplyCounter(postId: String)

    fun getAllTags(): Flow<List<Tag>>
    suspend fun insertTag(tag: Tag)
}