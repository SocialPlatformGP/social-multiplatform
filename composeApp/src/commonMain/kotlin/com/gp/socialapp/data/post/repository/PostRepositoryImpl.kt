package com.gp.socialapp.data.post.repository

import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.data.post.source.remote.PostRemoteDataSource
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.Platform
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.getPlatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class PostRepositoryImpl(
    private val postLocalSource: PostLocalDataSource,
    private val postRemoteSource: PostRemoteDataSource,
) : PostRepository {


    override suspend fun createPost(post: Post): Flow<Result<String>> {
        return postRemoteSource.createPost(post)
    }

    override suspend fun insertLocalPost(post: Post) {
        postLocalSource.insertPost(post)
    }

    override fun getAllPosts(scope: CoroutineScope): Flow<List<Post>> {
        val databaseMutex = Mutex()
        if(getPlatform() == Platform.JS) {
            return getRemotePosts()
        } else {
            scope.launch {
                while (true) {
                    launch {
                        val posts = postRemoteSource.fetchPosts()
                        databaseMutex.withLock {
                            posts.collect {
                                it.forEach { post ->
                                    insertLocalPost(post)
                                }
                            }
                        }
                    }
                    delay(60000)
                }
            }
            return getAllLocalPosts()
        }
    }

    override fun getRemotePosts(): Flow<List<Post>> {
        TODO("Not yet implemented")
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

    override suspend fun deleteLocalPostById(id: String) {
        postLocalSource.deletePostById(id)
    }


    override fun fetchNetworkPosts(): Flow<List<Post>> {
        return postRemoteSource.fetchPosts()
    }

    override suspend fun updatePost(post: Post):Flow<Result<String>> =
        postRemoteSource.updatePost(post)


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


    override suspend fun incrementReplyCounter(postId: String) =
        postRemoteSource.incrementReplyCounter(postId)

    override suspend fun decrementReplyCounter(postId: String) =
        postRemoteSource.decrementReplyCounter(postId)

    override fun getAllTags() = postRemoteSource.getAllTags()

    override suspend fun insertTag(tag: Tag) = postRemoteSource.insertTag(tag)
}