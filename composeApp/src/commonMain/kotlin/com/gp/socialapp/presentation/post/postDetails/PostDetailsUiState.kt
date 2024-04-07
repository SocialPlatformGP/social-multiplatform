package com.gp.socialapp.presentation.post.postDetails

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.post.source.remote.model.NestedReply
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Reply

data class PostDetailsUiState(
    val post: Post = Post(),
    val currentUser: User = User(),
    val isLoading: Boolean = false,
    val currentReplies: NestedReply = NestedReply(null, emptyList()),
    val currentReply: Reply = Reply(),
    val actionResult: PostDetailsActionResult = PostDetailsActionResult.NoActionResult
)
