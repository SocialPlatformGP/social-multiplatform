package com.gp.socialapp.presentatDefaultn.post.postDetails

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.post.repository.PostRepository
import com.gp.socialapp.data.post.repository.ReplyRepository
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.data.post.util.ToNestedReplies.toNestedReplies
import com.gp.socialapp.presentation.post.feed.PostEvent
import com.gp.socialapp.presentation.post.feed.ReplyEvent
import com.gp.socialapp.presentation.post.postDetails.PostDetailsActionResult
import com.gp.socialapp.presentation.post.postDetails.PostDetailsUiState
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailsScreenModel(
    private val postRepo: PostRepository,
    private val replyRepo: ReplyRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(PostDetailsUiState())
    val uiState = _uiState.asStateFlow()
    fun initScreenModel(post: Post) {
        screenModelScope.launch {
            _uiState.update { it.copy(post = post) }
            getRepliesById(post.id)
        }
    }

    private fun getRepliesById(id: String) {
        screenModelScope.launch(Dispatchers.Default) {
            replyRepo.getReplies(id).collect { result ->
                when (result) {
                    is Result.SuccessWithData -> {
                        val nestedReplies = result.data.toNestedReplies()
                        _uiState.update {
                            it.copy(
                                currentReplies = nestedReplies,
                                isLoading = false
                            )
                        }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                actionResult = PostDetailsActionResult.NetworkError(
                                    result.message
                                ), isLoading = false
                            )
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun insertReply(reply: Reply) {
        screenModelScope.launch(Dispatchers.Default) {
            val result = replyRepo.insertReply(reply)
            when (result) {
                is Result.Success -> {
                    getRepliesById(reply.postId)
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message
                            )
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    private fun reportReply(reply: Reply) {
        screenModelScope.launch(Dispatchers.Default) {
            val result = replyRepo.reportReply(reply.id, _uiState.value.currentUser.id)
            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.ReplyReported) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message
                            )
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    private fun updatePost() {
        screenModelScope.launch(Dispatchers.Default) {
//            TODO
        }
    }

    private fun upvotePost(post: Post) {
        screenModelScope.launch(Dispatchers.Default) {
            val result = postRepo.upvotePost(post)
            when (result) {
                is Result.Success -> {
                    updatePost()
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message
                            )
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    private fun downvotePost(post: Post) {
        screenModelScope.launch(Dispatchers.Default) {
            val result = postRepo.downvotePost(post)
            when (result) {
                is Result.Success -> {
                    updatePost()
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message
                            )
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    private fun deletePost(post: Post) {
        screenModelScope.launch(Dispatchers.Default) {
            val result = postRepo.deletePost(post)
            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.PostDeleted) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message
                            )
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    private fun updatePost(post: Post) {
        screenModelScope.launch(Dispatchers.Default) {
            val result = postRepo.updatePost(post)
            when (result) {
                is Result.Success -> {
                    updatePost()
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.PostUpdated) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message
                            )
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    private fun upvoteReply(reply: Reply) {
        screenModelScope.launch(Dispatchers.Default) {
            val result = replyRepo.upvoteReply(reply.id, _uiState.value.currentUser.id)
            when (result) {
                is Result.Success -> {
                    getRepliesById(reply.postId)
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message
                            )
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    private fun downvoteReply(reply: Reply) {
        screenModelScope.launch(Dispatchers.Default) {
            val result = replyRepo.downvoteReply(reply.id, _uiState.value.currentUser.id)
            when (result) {
                is Result.Success -> {
                    getRepliesById(reply.postId)
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message
                            )
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    private fun deleteReply(reply: Reply) {
        screenModelScope.launch(Dispatchers.Default) {
            val result = replyRepo.deleteReply(reply.id)
            when (result) {
                is Result.Success -> {
                    getRepliesById(reply.postId)
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.ReplyDeleted) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message
                            )
                        )
                    }
                }

                else -> Unit
            }
        }

    }

    private fun updateReply(reply: Reply) {
        screenModelScope.launch(Dispatchers.Default) {
            val result = replyRepo.updateReply(reply)
            when (result) {
                is Result.Success -> {
                    getRepliesById(reply.postId)
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.ReplyUpdated) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message
                            )
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    fun resetActionResult() {
        screenModelScope.launch {
            _uiState.update { it.copy(actionResult = PostDetailsActionResult.NoActionResult) }
        }
    }

    fun handlePostEvent(event: PostEvent) {
        when (event) {
            is PostEvent.OnPostDeleted -> deletePost(event.post)
            is PostEvent.OnPostUpVoted -> upvotePost(event.post)
            is PostEvent.OnPostDownVoted -> downvotePost(event.post)
            is PostEvent.OnCommentAdded -> {
                val reply = Reply(
                    postId = event.postId,
                    parentReplyId = "-1",
                    depth = 0,
                    content = event.text,
                    authorID = _uiState.value.currentUser.id,
                )
                println("reply in screen model: $reply")
                insertReply(reply)
            }

            else -> {}
        }
    }

    fun handleReplyEvent(event: ReplyEvent) {
        when (event) {
            is ReplyEvent.OnReplyDeleted -> deleteReply(event.reply)
            is ReplyEvent.OnReplyUpVoted -> upvoteReply(event.reply)
            is ReplyEvent.OnReplyDownVoted -> downvoteReply(event.reply)
            is ReplyEvent.OnReplyAdded -> {
                val reply = Reply(
                    postId = event.reply.postId,
                    parentReplyId = event.reply.id,
                    depth = event.reply.depth + 1,
                    content = event.text,
                    authorID = _uiState.value.currentUser.id,
                )
                println("nested reply in screen model: $reply")
                insertReply(reply)
            }

            is ReplyEvent.OnReplyReported -> {
                reportReply(event.reply)
            }

            else -> {}
        }
    }
}