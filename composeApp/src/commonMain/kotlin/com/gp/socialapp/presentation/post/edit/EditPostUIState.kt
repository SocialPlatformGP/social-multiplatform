package com.gp.socialapp.presentation.post.edit

import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.data.post.source.remote.model.Tag

data class EditPostUIState(
    val post: Post = Post(),
    val editSuccess: Boolean = false,
    val title: String = "",
    val body: String = "",
    val channelTags: Set<Tag> = emptySet(),
    val postTags: Set<Tag> = emptySet(),
    val postAttachments: List<PostAttachment> = emptyList()
)