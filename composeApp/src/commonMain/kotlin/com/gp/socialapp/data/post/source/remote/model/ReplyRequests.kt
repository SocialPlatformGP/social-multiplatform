package com.gp.socialapp.data.post.source.remote.model

sealed class ReplyRequest{
    data class CreateRequest(val reply: Reply): ReplyRequest()
    data class FetchRequest(val postId: String): ReplyRequest()
    data class UpdateRequest(val reply: Reply): ReplyRequest()
    data class UpvoteRequest(val replyId: String, val userId: String): ReplyRequest()
    data class DownvoteRequest(val replyId: String, val userId: String): ReplyRequest()
    data class DeleteRequest(val replyId: String): ReplyRequest()
}