package com.gp.socialapp.presentation.post.postDetails

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.post.repository.PostRepository
import com.gp.socialapp.data.post.repository.ReplyRepository
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.data.post.util.ToNestedReplies.toNestedReplies
import com.gp.socialapp.presentation.post.feed.PostEvent
import com.gp.socialapp.presentation.post.feed.ReplyEvent
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Results
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailsScreenModel(
    private val postRepo: PostRepository,
    private val replyRepo: ReplyRepository,
    private val authRepo: AuthenticationRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(PostDetailsUiState())
    val uiState = _uiState.asStateFlow()
    fun initScreenModel(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val userId = authRepo.getCurrentLocalUserId()
            _uiState.update { it.copy(post = post, currentUserId = userId) }
            getRepliesById(post.id)
        }
    }

    private fun getRepliesById(id: String) {
        screenModelScope.launch(DispatcherIO) {
            replyRepo.getReplies(id).collect { result ->
                when (result) {
                    is Results.Success -> {
                        val nestedReplies = result.data.toNestedReplies()
                        _uiState.update {
                            it.copy(
                                currentReplies = nestedReplies,
                                isLoading = false
                            )
                        }
                    }

                    is Results.Failure -> {
                        _uiState.update {
                            it.copy(
                                actionResult = PostDetailsActionResult.NetworkError(
                                    result.error.userMessage
                                ), isLoading = false
                            )
                        }
                    }

                    is Results.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun insertReply(reply: Reply) {
        screenModelScope.launch(DispatcherIO) {
            val result = replyRepo.insertReply(reply)
            when (result) {
                is Results.Success -> {
                    getRepliesById(reply.postId)
                }

                is Results.Failure -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.error.userMessage
                            )
                        )
                    }
                }

                Results.Loading -> {
                    // TODO
                }
            }
        }
    }

    private fun reportReply(reply: Reply) {
        screenModelScope.launch(DispatcherIO) {
            val result = replyRepo.reportReply(reply.id, _uiState.value.currentUserId)
            when (result) {
                is Results.Success -> {
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.ReplyReported) }
                }

                is Results.Failure -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.error.userMessage
                            )
                        )
                    }
                }

                Results.Loading -> {
                    // TODO
                }
            }
        }
    }

    private fun updatePost() {
        screenModelScope.launch(DispatcherIO) {
//            TODO
        }
    }

    private fun upvotePost(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.upvotePost(post, _uiState.value.currentUserId)
            when (result) {
                is Results.Failure -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.error.userMessage
                            )
                        )
                    }
                }

                Results.Loading -> {
                    // TODO
                }

                is Results.Success -> {
                    updatePost()
                }
            }
        }
    }

    private fun downvotePost(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.downvotePost(post, _uiState.value.currentUserId)
            when (result) {
                is Results.Success -> {
                    updatePost()
                }

                is Results.Failure -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.error.userMessage
                            )
                        )
                    }
                }

                Results.Loading -> {
                    // TODO
                }
            }
        }
    }

    private fun deletePost(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.deletePost(post)
            when (result) {
                is Results.Success -> {
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.PostDeleted) }
                }

                is Results.Failure -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.error.userMessage
                            )
                        )
                    }
                }

                Results.Loading -> {
                    // TODO

                }
            }
        }
    }

    private fun updatePost(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.updatePost(post)
            when (result) {
                is Results.Success -> {
                    updatePost()
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.PostUpdated) }
                }

                is Results.Failure -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.error.userMessage
                            )
                        )
                    }
                }

                Results.Loading -> {
                    // TODO
                }
            }
        }
    }

    private fun upvoteReply(reply: Reply) {
        screenModelScope.launch(DispatcherIO) {
            val result =
                replyRepo.upvoteReply(reply.id, currentUserId = _uiState.value.currentUserId)
            when (result) {
                is Results.Success -> {
                    getRepliesById(reply.postId)
                }

                is Results.Failure -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.error.userMessage
                            )
                        )
                    }
                }

                Results.Loading -> {
                    // TODO
                }
            }
        }
    }

    private fun downvoteReply(reply: Reply) {
        screenModelScope.launch(DispatcherIO) {
            val result = replyRepo.downvoteReply(reply.id, _uiState.value.currentUserId)
            when (result) {
                is Results.Success -> {
                    getRepliesById(reply.postId)
                }

                is Results.Failure -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.error.userMessage
                            )
                        )
                    }
                }

                Results.Loading -> {
                    // TODO
                }
            }
        }
    }

    private fun deleteReply(reply: Reply) {
        screenModelScope.launch(DispatcherIO) {
            val result = replyRepo.deleteReply(reply.id)
            when (result) {
                is Results.Success -> {
                    getRepliesById(reply.postId)
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.ReplyDeleted) }
                }

                is Results.Failure -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.error.userMessage
                            )
                        )
                    }
                }

                Results.Loading -> {
                    // TODO
                }
            }
        }

    }

    private fun updateReply(reply: Reply) {
        screenModelScope.launch(DispatcherIO) {
            val result = replyRepo.updateReply(reply)
            when (result) {
                is Results.Success -> {
                    getRepliesById(reply.postId)
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.ReplyUpdated) }
                }

                is Results.Failure -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.error.userMessage
                            )
                        )
                    }
                }

                Results.Loading -> {
                    // TODO
                }
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
                    authorID = _uiState.value.currentUserId,
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
                    authorID = _uiState.value.currentUserId,
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