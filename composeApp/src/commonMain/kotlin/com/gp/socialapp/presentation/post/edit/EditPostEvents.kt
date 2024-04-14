package com.gp.socialapp.presentation.post.edit

import com.gp.socialapp.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.data.post.source.remote.model.Tag

sealed class EditPostEvents() {
    object Init : EditPostEvents()
    object NavigateBack : EditPostEvents()
    object OnAddImageClicked : EditPostEvents()
    object OnAddVideoClicked : EditPostEvents()
    object OnAddFileClicked : EditPostEvents()
    data class OnPreviewClicked(val file: PostAttachment) : EditPostEvents()
    data class OnTitleChanged(val title: String) : EditPostEvents()
    data class OnContentChanged(val content: String) : EditPostEvents()
    object OnApplyEditClicked : EditPostEvents()
    data class OnTagAdded(val tags: Set<Tag>) : EditPostEvents()
    data class OnTagRemoved(val tag: Tag) : EditPostEvents()
    data class OnFileAdded(val files: List<PostAttachment>) : EditPostEvents()
    data class OnFileRemoved(val file: PostAttachment) : EditPostEvents()


}