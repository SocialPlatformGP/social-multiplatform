package com.gp.socialapp.presentation.app.com.gp.socialapp.data.post.source.local

import com.gp.socialapp.data.post.source.local.PostLocalDataSource
import com.gp.socialapp.data.post.source.remote.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object DummyLocalSource: PostLocalDataSource {
    override suspend fun insertPost(post: Post){}

    override fun getAllPosts(): Flow<List<Post>> = flow{}

    override fun getPostById(id: String): Flow<Post> = flow{}

    override suspend fun deletePostById(id: String) {}

    override suspend fun deleteAllPosts() {}
}