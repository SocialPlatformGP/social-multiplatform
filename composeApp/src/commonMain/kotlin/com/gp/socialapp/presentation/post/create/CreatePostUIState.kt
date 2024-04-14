package com.gp.socialapp.presentation.post.create

import com.gp.socialapp.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.presentation.post.feed.FeedTab


data class CreatePostUIState(
    var userProfilePicURL: String = "",
    var title: String = "",
    var body: String = "",
    var createdState: Boolean = false,
    var cancelPressed: Boolean = false,
    var tags: List<Tag> = emptyList(),
    var type: String = FeedTab.entries.first().title,
    var files: List<PostAttachment> = emptyList(),
)
