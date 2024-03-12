package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.data.post.source.remote.PostRemoteDataSource
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Tag
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(
    private val postLocalSource: PostLocalDataSource,
    private val postRemoteSource: PostRemoteDataSource,
): PostRepository{
    override suspend fun insertLocalPost(vararg post: Post) {
        postLocalSource.insertPost(*post)
    }

    override suspend fun updateLocalPost(post: Post) {
        postLocalSource.updatePost(post)
    }

    override fun getAllLocalPosts(): Flow<List<Post>> {
//        if (networkStatus.isOnline()) {
//            val posts = postRemoteSource.fetchPosts()
//            repositoryScope.launch {
//                postLocalSource.deleteAllPosts()
//                posts.collect {
//                    it.forEach { post ->
//                        insertLocalPost(post.toEntity())
//                    }
//                }
//            }
//            return posts
//        } else {
//            return postLocalSource.getAllPosts().toPostFlow()
//        }
        return postLocalSource.getAllPosts()
    }

    override suspend fun deleteLocalPost(post: Post) {
        postLocalSource.deletePost(post)
    }


    override fun fetchNetworkPosts(): Flow<List<Post>> {
        return postRemoteSource.fetchPosts()
    }

    override suspend fun updatePost(post: Post) {
        postRemoteSource.updatePost(post)
    }

    override suspend fun deletePost(post: Post) {
        postRemoteSource.deletePost(post)
    }

//    override fun createPost(post: Post, files: List<PostFile>): Flow<State<Nothing>> {
//        return if(files.isEmpty()) {
//            postRemoteSource.createPost(post)
//        } else {
//            postRemoteSource.createPostWithFiles(post, files)
//        }
//    }

    override fun onCleared() {
//        repositoryScope.cancel()
    }

    override fun searchPostsByTitle(searchText: String): Flow<List<Post>> {
        return postLocalSource.searchPostsByTitle(searchText)
    }

    override suspend fun upVotePost(post: Post) = postRemoteSource.upVotePost(post)

    override suspend fun downVotePost(post: Post) = postRemoteSource.downVotePost(post)
    override fun fetchPostById(id: String): Flow<Post> {
//        if (networkStatus.isOnline()) {
//            val post = postRemoteSource.fetchPostById(id)
//            repositoryScope.launch {
//                post.collect {
//                    insertLocalPost(it.toEntity())
//                }
//            }
//            return post
//        }
//        else{
//            return postLocalSource.getPostById(id).toModel()
//        }
        return postLocalSource.getPostById(id)
    }

    override fun deleteAllPosts() {

    }


    override suspend fun incrementReplyCounter(postId: String) = postRemoteSource.incrementReplyCounter(postId)

    override suspend fun decrementReplyCounter(postId: String) = postRemoteSource.decrementReplyCounter(postId)
    override fun getAllTags()=postRemoteSource.getAllTags()

    override suspend fun insertTag(tag: Tag) = postRemoteSource.insertTag(tag)
}