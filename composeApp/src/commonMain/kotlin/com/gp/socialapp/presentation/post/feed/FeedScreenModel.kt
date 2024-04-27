package com.gp.socialapp.presentation.post.feed

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.community.repository.CommunityRepository
import com.gp.socialapp.data.post.repository.PostRepository
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.data.post.util.PostPopularityUtils
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Results
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedScreenModel(
    private val postRepo: PostRepository,
    private val authRepo: AuthenticationRepository,
    private val communityRepository: CommunityRepository
) : ScreenModel {
    private val _state = MutableStateFlow(FeedUiState())
    val state = _state.asStateFlow()

    fun initScreen(communityId: String) {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.SuccessWithData -> {
                        _state.update {
                            it.copy(
                                currentUserID = result.data.id,
                                currentUser = result.data
                            )
                        }
                    }

                    is Result.Error -> {
                        Napier.e("Error: ${result.message}")
                        _state.update { it.copy(error = FeedError.NetworkError(result.message)) }
                    }

                    else -> Unit
                }
            }
            getAllPosts()
        }
        getCurrentCommunity(communityId)
    }

    private fun getCurrentCommunity(communityId: String) {
        screenModelScope.launch(DispatcherIO) {
            communityRepository.fetchCommunity(communityId).collect { result ->
                when (result) {
                    is Results.Success -> {
                        _state.update {
                            it.copy(
                                currentCommunity = result.data
                            )
                        }
                    }

                    is Results.Failure -> {
                        _state.update {
                            it.copy(
                                error = FeedError.NetworkError(result.error.userMessage)
                            )
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun getAllPosts() {
        screenModelScope.launch(DispatcherIO) {
            postRepo.getPosts().collectLatest { result ->
                when (result) {
                    is Results.Success -> {
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

                    is Results.Failure -> {
                        _state.update {
                            it.copy(
                                isFeedLoaded = Result.Error(result.error.userMessage),
                                error = FeedError.NetworkError(result.error.userMessage)
                            )
                        }
                    }

                    is Results.Loading -> {
                        //todo
                    }
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
                is Results.Failure -> {
                    _state.update { it.copy(error = FeedError.NetworkError(result.error.userMessage)) }
                }

                is Results.Success -> {
                    getAllPosts()
                }

                Results.Loading -> {
                    //todo
                }
            }

        }
    }

    fun downVote(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.downvotePost(post, state.value.currentUserID)
            when (result) {
                is Results.Failure -> {
                    _state.update { it.copy(error = FeedError.NetworkError(result.error.userMessage)) }
                }

                is Results.Success -> {
                    getAllPosts()
                }

                Results.Loading -> {
                    //todo
                }
            }
        }
    }

    fun reportPost(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.reportPost(post.id, state.value.currentUserID)
            if (result is Results.Failure) {
                _state.update { it.copy(error = FeedError.NetworkError(result.error.userMessage)) }
            }
        }
    }

    fun deletePost(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.deletePost(post)
            when (result) {
                is Results.Failure -> {
                    _state.update { it.copy(error = FeedError.NetworkError(result.error.userMessage)) }
                }

                is Results.Success -> {
                    getAllPosts()
                }

                Results.Loading -> {
                    //todo
                }
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

    fun changeOpenedTab(tab: Int) {
        _state.update {
            it.copy(openedTabItem = FeedTab.entries[tab])
        }
    }

    fun logout() {
        screenModelScope.launch {
            authRepo.logout()
            _state.update { it.copy(isLoggedOut = true) }
        }
    }

    fun resetState() {
        screenModelScope.launch {
            _state.update { FeedUiState() }
        }
    }

    fun openAttachment(attachment: PostAttachment) {
        screenModelScope.launch (DispatcherIO) {
            val mimeType = MimeType.getMimeTypeFromFileName(attachment.name).mimeType
            postRepo.openAttachment(attachment.url, mimeType)
        }
    }
}