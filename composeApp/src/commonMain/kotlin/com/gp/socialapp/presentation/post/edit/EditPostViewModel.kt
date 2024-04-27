package com.gp.socialapp.presentation.post.edit

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.post.repository.PostRepository
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.data.post.source.remote.model.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditPostScreenModel(
    private val postRepository: PostRepository
) : ScreenModel {
    val _post = MutableStateFlow(Post())
    val post = _post.asStateFlow()
    val channelTags = MutableStateFlow(emptyList<Tag>())
    val tags = channelTags.asStateFlow()
    val success = MutableStateFlow(false)

    fun updatePost() {
        with(post.value) {
            screenModelScope.launch(Dispatchers.Default) {
                postRepository.updatePost(
                    post.value.copy(
                        title = title,
                        body = body,
                    )
                )
            }
        }
    }

    fun getChannelTags(communityId: String) {
        screenModelScope.launch {
            postRepository.getAllTags(communityId).collect {
                channelTags.value = it
            }
        }
    }

    fun setPost(currentPost: Post) {
        _post.update { currentPost }
    }

    fun updateBody(body: String) {
        _post.update { it.copy(body = body) }
    }

    fun updateTitle(title: String) {
        _post.update { it.copy(title = title) }
    }

    fun addTag(tags: List<Tag>) {
        val tags2 = post.value.tags + tags
        _post.update { it.copy(tags = tags2.distinct()) }
    }

    fun removeTag(tag: Tag) {
        _post.update { it.copy(tags = it.tags - tag) }
    }

    fun addFile(files: List<PostAttachment>) {
        val files2 = post.value.attachments + files

        _post.update { it.copy(attachments = files2.distinct()) }
    }

    fun removeFile(file: PostAttachment) {
        _post.update { it.copy(attachments = it.attachments - file) }
    }

    fun handleEvent(event: EditPostEvents) {
        when (event) {
            is EditPostEvents.OnTitleChanged -> updateTitle(event.title)
            is EditPostEvents.OnContentChanged -> updateBody(event.content)
            is EditPostEvents.OnTagAdded -> addTag(event.tags.toList())
            is EditPostEvents.OnTagRemoved -> removeTag(event.tag)
            is EditPostEvents.OnFileRemoved -> removeFile(event.file)
            is EditPostEvents.OnFileAdded -> addFile(event.files)
            is EditPostEvents.OnApplyEditClicked -> updatePost()
            else -> Unit
        }
    }

}