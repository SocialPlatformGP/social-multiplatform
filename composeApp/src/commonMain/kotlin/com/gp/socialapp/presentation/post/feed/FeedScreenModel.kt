package com.gp.socialapp.presentation.post.feed

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.post.repository.PostRepository
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.util.PostPopularityUtils
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedScreenModel(
    private val postRepo: PostRepository,
    private val authRepo: AuthenticationRepository
) : ScreenModel {
    private val _state = MutableStateFlow(FeedUiState())
    val state = _state.asStateFlow()
    init {
        screenModelScope.launch (DispatcherIO){
            val userId = authRepo.getCurrentLocalUserId()
            _state.update { it.copy(currentUserID = userId) }
            getAllPosts()
        }

    }
    private fun getAllPosts() {
        screenModelScope.launch(DispatcherIO) {
            postRepo.getPosts().collectLatest { result ->
                when (result) {
                    is Result.SuccessWithData -> {
                        result.data.forEach { post ->
                            _state.update { it.copy(allTags = _state.value.allTags + post.tags) }
                        }
                        val filteredPosts = if (_state.value.selectedTags.isNotEmpty()) {
                            result.data.filter { post ->
                                post.tags.intersect(_state.value.selectedTags).isNotEmpty()
                            }
                        } else {
                            result.data
                        }
                        val sortedPosts = if (_state.value.isSortedByNewest) {
                            filteredPosts.sortedByDescending { it.createdAt }
                        } else {
                            filteredPosts.sortedByDescending {
                                PostPopularityUtils.calculateInteractionValue(
                                    it.votes,
                                    it.replyCount
                                )
                            }
                        }
                        _state.update {
                            it.copy(
                                posts = sortedPosts,
                                isFeedLoaded = Result.Success
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isFeedLoaded = Result.Error(result.message),
                                error = FeedError.NetworkError(result.message)
                            )
                        }
                    }

                    is Result.Loading -> { /*do nothing*/
                    }

                    else -> Unit
                }
            }
        }
    }

    fun resetError() {
        screenModelScope.launch {
            _state.update { it.copy(error = FeedError.NoError) }
        }
    }

    fun upVote(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.upvotePost(post, state.value.currentUserID)
            when (result) {
                is Result.Error -> {
                    _state.update { it.copy(error = FeedError.NetworkError(result.message)) }
                }
                is Result.Success -> {
                    getAllPosts()
                }

                else -> Unit
            }

        }
    }

    fun downVote(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.downvotePost(post, state.value.currentUserID)
            when (result) {
                is Result.Error -> {
                    _state.update { it.copy(error = FeedError.NetworkError(result.message)) }
                }

                is Result.Success -> {
                    getAllPosts()
                }

                else -> Unit
            }
        }
    }

    fun reportPost(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.reportPost(post.id, state.value.currentUserID)
            if (result is Result.Error) {
                _state.update { it.copy(error = FeedError.NetworkError(result.message)) }
            }
        }
    }

    fun deletePost(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.deletePost(post)
            when (result) {
                is Result.Error -> {
                    _state.update { it.copy(error = FeedError.NetworkError(result.message)) }
                }

                is Result.Success -> {
                    getAllPosts()
                }

                else -> Unit
            }
        }
    }

    fun sortPostsByNewest() {
        screenModelScope.launch {
            _state.update { it.copy(isSortedByNewest = true) }
            getAllPosts()
        }
    }

    fun sortPostsByPopularity() {
        screenModelScope.launch {
            _state.update { it.copy(isSortedByNewest = false) }
            getAllPosts()
        }
    }

    fun updateTagFilters(newFilters: List<String>) {
        _state.update {
            it.copy(selectedTags = _state.value.allTags.filter { newFilters.contains(it.label) }
                .toSet())
        }
    }
    fun changeOpenedTab(tab: Int){
        _state.update {
            it.copy(openedTabItem = FeedTab.entries[tab])
        }
    }
}