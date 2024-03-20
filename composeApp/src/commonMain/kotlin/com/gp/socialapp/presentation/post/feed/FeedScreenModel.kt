package com.gp.socialapp.presentation.post.feed

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.post.repository.PostRepository
import com.gp.socialapp.data.post.repository.ReplyRepository
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.util.PostPopularityUtils
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedScreenModel(
    val repository: PostRepository,
    val replyRepository: ReplyRepository,
    val userRepository: UserRepository
) : ScreenModel {
    private val _state = MutableStateFlow(FeedUiState())
    val state = _state.asStateFlow()

    fun getAllPosts() {
        screenModelScope.launch(Dispatchers.Default) {
            repository.getAllPosts().collectLatest { result ->
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
                                error = FeedError.NetworkError(result.message))
                        }
                    }
                    is Result.Loading -> { /*do nothing*/}
                    else -> Unit
                }
            }
        }
    }

    fun upVote(post: Post) {
        screenModelScope.launch {
            repository.upVotePost(post)
        }
    }

    fun downVote(post: Post) {
        screenModelScope.launch() {
            repository.downVotePost(post)
        }
    }

    fun deletePost(post: Post) {
        screenModelScope.launch() {
            repository.deletePost(post)
            repository.deleteLocalPostById(post.id)
        }
    }

    fun sortPostsByNewest() {
//        viewModelScope.launch {
//            _isSortedByNewest.value = true
//            val posts = (uiState.value as? State.SuccessWithData)?.data ?: return@launch
//            withContext(Dispatchers.Default) {
//                Collections.sort(posts, Post.sortByDate)
//            }
//            withContext(Dispatchers.Main) {
//                _uiState.value = State.Loading
//                _uiState.value = State.SuccessWithData(posts)
//            }
//            unfilteredPosts.clear()
//            unfilteredPosts.addAll(posts)
//            Log.d("TAG258", "New Data by Newest: ${(uiState.value as? State.SuccessWithData<List<Post>>)?.data?.map{"${it.title} : ${it.votes}"} ?: emptyList()}")
//        }
        screenModelScope.launch {
            _state.update { it.copy(isSortedByNewest = true) }
            getAllPosts()
        }
    }

    fun sortPostsByPopularity() {
//        viewModelScope.launch {
//            _isSortedByNewest.value = false
//            val posts = (uiState.value as? State.SuccessWithData)?.data ?: return@launch
//
//            withContext(Dispatchers.Default) {
//                Collections.sort(posts, Post.sortByVotes)
//            }
//            withContext(Dispatchers.Main) {
//                _uiState.value = State.Loading
//                _uiState.value = State.SuccessWithData(posts)
//            }
//            unfilteredPosts.clear()
//            unfilteredPosts.addAll(posts)
//            Log.d("TAG258", "New Data by most popular: ${(uiState.value as? State.SuccessWithData<List<Post>>)?.data?.map{"${it.title} : ${it.votes}"} ?: emptyList()}")
//        }
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
}