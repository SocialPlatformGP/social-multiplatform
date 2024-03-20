package com.gp.socialapp.data.post.source.remote.model

import kotlinx.serialization.Serializable


sealed class PostRequest{
    @Serializable
    data class FetchRequest( val lastUpdated: Long): PostRequest()
    @Serializable
    data class UpvoteRequest(val postId: String, val userId: String): PostRequest()
    @Serializable
    data class DownvoteRequest(val postId: String, val userId: String): PostRequest()
    @Serializable
    data class DeleteRequest(val postId: String): PostRequest()
}

