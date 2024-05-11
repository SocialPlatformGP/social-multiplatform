package com.gp.socialapp.presentation.userprofile

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.post.repository.PostRepository
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserProfileScreenModel(
    val userRepo: UserRepository,
    val authRepo: AuthenticationRepository,
    val postRepo: PostRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState = _uiState.asStateFlow()

    fun init(userId: String) {
        getUserData(userId)
        getUserPosts()

    }

    private fun getUserPosts() {
        screenModelScope.launch {
            postRepo.getUserPosts("").onSuccessWithData { data ->
                _uiState.update {
                    it.copy(posts = data)
                }
            }.onFailure {
                _uiState.update {
                    it.copy(error = it.error)
                }
            }
        }
    }

    private fun getUserData(userId: String) {
        screenModelScope.launch {
            userRepo.getUsersByIds(listOf(userId)).collect { result ->
                when (result) {
                    is com.gp.socialapp.util.Result.Success -> {
                        _uiState.update {
                            it.copy(user = result.data.first())
                        }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(error = result.message.userMessage)
                        }
                    }

                    Result.Loading -> {}
                }
            }
        }
    }


    override fun onDispose() {
        super.onDispose()

    }
}