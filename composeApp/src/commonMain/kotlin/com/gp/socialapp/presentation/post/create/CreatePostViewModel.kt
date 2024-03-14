package com.gp.socialapp.presentation.post.create

import cafe.adriel.voyager.core.model.ScreenModel
import com.gp.socialapp.data.post.source.remote.model.PostFile
import com.gp.socialapp.data.post.source.remote.model.Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreatePostViewModel(
//    private val postRepository: PostRepository,
//    private val userRepository: UserRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(CreatePostUIState())
    val uiState = _uiState.asStateFlow()


    init {
//        getCurrentUser()
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
//        screenModelScope.launch {
//            with(uiState.value) {
//                val state =
//                    postRepository.createPost(
//                        Post(
//                            userPfp = userProfilePicURL,
//                            userName = "${currentUser.value.userFirstName} ${currentUser.value.userLastName}",
//                            authorEmail = currentUserName!!,
//                            title = title,
//                            body = body,
//                            tags = tags,
//                            type = type,
//                            attachments = emptyList()
//                        ), uiState.value.files
//                    )
//                state.collect { newState ->
//                    uiState.value = uiState.value.copy(createdState = newState)
//                }
//            }
//            delay(500)
//        }
    }

    fun onCancel() {
//        uiState.value = uiState.value.copy(cancelPressed = true)
    }

    //
    fun resetCancelPressed() {
//        uiState.value.cancelPressed = false
    }

    fun getCurrentUser() {
//        screenModelScope.launch(Dispatchers.Default) {
//            when (userRepository.fetchUser(currentUserName!!)) {
//                is State.SuccessWithData -> {
//                    currentUser.value =
//                        (userRepository.fetchUser(currentUserName) as State.SuccessWithData<NetworkUser>).data
//                    uiState.value =
//                        uiState.value.copy(userProfilePicURL = currentUser.value.userProfilePictureURL)
//                }
//
//                else -> {}
//            }
//        }
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