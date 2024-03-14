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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedScreenModel(
    val repository: PostRepository,
    val replyRepository: ReplyRepository,
    val userRepository: UserRepository
): ScreenModel {
    private val _tags = mutableSetOf<String>()
    val tags: Set<String> = _tags
    private val _selectedTagFilters = MutableStateFlow<Set<String>>(emptySet())
    val selectedTagFilters = _selectedTagFilters.asStateFlow()
    private val _isSortedByNewest = MutableStateFlow(true)
    val isSortedByNewest = _isSortedByNewest.asStateFlow()
    private val _uiState = MutableStateFlow<Result<List<Post>>>(Result.Idle)
    val uiState = _uiState.asStateFlow()
    private val _state = MutableStateFlow(FeedUiState())
    val state = _state.asStateFlow()
    private fun getAllPosts() {
        screenModelScope.launch() {
            repository.getAllLocalPosts().collect { posts ->
                posts.forEach { post ->
                    _tags.addAll(post.tags.map{it.label})
                }
                val filteredPosts = if (selectedTagFilters.value.isNotEmpty()) {
                    posts.filter { post ->
                        post.tags.map { it.label }.intersect(selectedTagFilters.value).isNotEmpty()
                    }
                } else {
                    posts
                }
                val sortedPosts = if (isSortedByNewest.value) {
                    filteredPosts.sortedByDescending { /*DateUtils.convertStringToDate(it.publishedAt)*/ it.publishedAt }
                } else {
                    filteredPosts.sortedByDescending { PostPopularityUtils.calculateInteractionValue(it.votes, it.replyCount) }
                }
                withContext(Dispatchers.Main) {
                    _uiState.value = Result.SuccessWithData(sortedPosts)
                    _state.update { it.copy(posts = sortedPosts,  result = Result.Success) }
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
            repository.deleteLocalPost(post)
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
        _isSortedByNewest.value = true
        getAllPosts()
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
        _isSortedByNewest.value = false
        getAllPosts()
    }
    fun updateTagFilters(newFilters: List<String>){
        _selectedTagFilters.value = newFilters.toSet()
        getAllPosts()
    }
}