package com.gp.socialapp.presentation.post.create

import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.data.post.source.remote.model.Tag

sealed class CreatePostEvents() {
    object Init : CreatePostEvents()
    object NavigateBack : CreatePostEvents()
    object OnAddImageClicked : CreatePostEvents()
    object OnAddVideoClicked : CreatePostEvents()
    object OnAddFileClicked : CreatePostEvents()
    data class OnPreviewClicked(val file: PostFile) : CreatePostEvents()

    data class OnTitleChanged(val newTitle: String) : CreatePostEvents()

    data class OnBodyChanged(val newBody: String) : CreatePostEvents()
    object OnCreatePostClicked : CreatePostEvents()

    data class OnTagAdded(val tags: Set<Tag>) : CreatePostEvents()

    //
    data class OnTagRemoved(val tag: Tag) : CreatePostEvents()
    data class OnFileRemoved(val file: PostFile) : CreatePostEvents()

}