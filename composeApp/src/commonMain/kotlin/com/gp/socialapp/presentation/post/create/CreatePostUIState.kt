package com.gp.socialapp.presentation.post.create

import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.data.post.source.remote.model.Tag


data class CreatePostUIState(
    var userProfilePicURL: String = "",
    var title: String = "",
    var body: String = "",
    var createdState: Boolean = false,
    var cancelPressed: Boolean = false,
    var tags: List<Tag> = emptyList(),
    var type: String = "all",
    var files: List<PostFile> = emptyList(),
)
