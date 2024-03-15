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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatePostViewModel(
    private val postRepository: PostRepository,
    private val authRepository: AuthenticationRepository,
) : ScreenModel {
    private val _uiState = MutableStateFlow(CreatePostUIState())
    val uiState = _uiState.asStateFlow()
    val currentUser = MutableStateFlow(User())


    init {
        getCurrentUser()
//        getChannelTags()
    }


    fun getChannelTags() {
//        screenModelScope.launch {
//            postRepository.getAllTags().collect {
//                channelTags.value = it
//            }
//        }
    }

    //
    fun insertNewTags(tags: List<Tag>) {
//        screenModelScope.launch {
//            tags.forEach {
//                postRepository.insertTag(it)
//            }
//        }
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
                    )
                ).collect {
                    when (it) {
                        is Result.SuccessWithData -> {
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
//        viewModelScope.launch {
//            uiState.update { it.copy(files = it.files + postFile) }
//        }
    }

    fun removeFile(file: PostFile) {
//        viewModelScope.launch(Dispatchers.Default) {
//            val newFiles = uiState.value.files.filter { it.uri != file.uri }
//            uiState.value = uiState.value.copy(files = newFiles)
//        }
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
//        uiState.update { it.copy(tags = it.tags + tag) }
    }

    //
    fun onRemoveTag(tag: Tag) {
//        uiState.update { it.copy(tags = it.tags - tag) }
    }

}