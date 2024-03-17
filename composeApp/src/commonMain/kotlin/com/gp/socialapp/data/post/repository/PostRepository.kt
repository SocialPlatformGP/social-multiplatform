package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun insertLocalPost(vararg post: Post)
    suspend fun updateLocalPost(post: Post)
    fun getAllLocalPosts(): Flow<List<Post>>
    suspend fun deleteLocalPost(post: Post)
    fun fetchNetworkPosts(): Flow<List<Post>>
    suspend fun updatePost(post: Post): Flow<Result<String>>
    suspend fun deletePost(post: Post)

    //    fun createPost(post: Post, files: List<PostFile>): Flow<State<Nothing>>
    fun onCleared()
    fun searchPostsByTitle(searchText: String): Flow<List<Post>>
    suspend fun upVotePost(post: Post)
    suspend fun downVotePost(post: Post)
    fun fetchPostById(id: String): Flow<Post>
    fun deleteAllPosts()
    suspend fun createPost(post: Post): Flow<Result<String>>

    suspend fun incrementReplyCounter(postId: String)
    suspend fun decrementReplyCounter(postId: String)
    fun getAllTags(): Flow<List<Tag>>
    suspend fun insertTag(tag: Tag)
}