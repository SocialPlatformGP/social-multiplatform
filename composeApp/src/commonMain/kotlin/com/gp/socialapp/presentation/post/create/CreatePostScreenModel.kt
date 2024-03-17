package com.gp.socialapp.presentation.post.create

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.data.post.repository.PostRepository
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.data.post.source.remote.model.Tag
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatePostScreenModel(
    private val postRepository: PostRepository,
    private val authRepository: AuthenticationRepository,
) : ScreenModel {
    private val _uiState = MutableStateFlow(CreatePostUIState())
    val uiState = _uiState.asStateFlow()
    val currentUser = MutableStateFlow(User())
    val channelTags = MutableStateFlow<List<Tag>>(emptyList())


    init {
//        getCurrentUser()
        getChannelTags()
    }


    private fun getChannelTags() {
        screenModelScope.launch {
            postRepository.getAllTags().collect { tags ->

                channelTags.update { tags }
                println(channelTags.value)
            }
        }
    }

    //
    fun insertNewTags(tags: Set<Tag>) {
        screenModelScope.launch {
            tags.toSet().forEach {
                postRepository.insertTag(it)
            }
        }
        _uiState.update { it.copy(tags = (uiState.value.tags + tags)) }
    }

    fun onCreatePost() {
        screenModelScope.launch {
            with(uiState.value) {
                postRepository.createPost(
                    Post(
                        title = title,
                        body = body,
                        tags = tags,
                        type = type,
                        userName = currentUser.value.firstName + " " + currentUser.value.lastName,
                        userPfp = currentUser.value.profilePictureURL,
                        authorEmail = currentUser.value.email,
                        attachments = files
                    )
                ).collect { it ->
                    when (it) {
                        is Result.SuccessWithData -> {
                            _uiState.update { it.copy(createdState = true) }
                            println(it.data)
                        }

                        is Result.Error -> {
                            println(it.message)
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    fun onCancel() {
//        uiState.value = uiState.value.copy(cancelPressed = true)
    }

    //
    fun resetCancelPressed() {
//        uiState.value.cancelPressed = false
    }

    private fun getCurrentUser() {
        screenModelScope.launch {
            authRepository.getCurrentLocalUserId().let { id ->
                authRepository.getSignedInUser(id).collect {
                    when (it) {
                        is Result.SuccessWithData -> {
                            currentUser.value = it.data
                        }

                        is Result.Error -> {
                            println("Error: cant get user data")
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    //
    fun setType(type: String) {
//        uiState.value = uiState.value.copy(type = type)
    }

    fun addFile(postFile: PostFile) {
        screenModelScope.launch {
            _uiState.update { it.copy(files = it.files + postFile) }
        }
    }

    fun removeFile(file: PostFile) {
        screenModelScope.launch(Dispatchers.Default) {
            val newFiles = uiState.value.files.filter { !it.file.contentEquals(file.file) }
            _uiState.value = uiState.value.copy(files = newFiles)
        }
    }

    //
    fun onTitleChange(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    //
    fun onBodyChange(body: String) {
        _uiState.update { it.copy(body = body) }
    }

    //
    fun onAddTag(tag: Set<Tag>) {
        _uiState.update { it.copy(tags = it.tags + tag) }
    }

    //
    fun onRemoveTag(tag: Tag) {
        _uiState.update { it.copy(tags = it.tags - tag) }
    }

}