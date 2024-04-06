package com.gp.socialapp.presentatDefaultn.post.postDetails

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.post.repository.PostRepository
import com.gp.socialapp.data.post.repository.ReplyRepository
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.presentation.post.feed.PostEvent
import com.gp.socialapp.presentation.post.feed.ReplyEvent
import com.gp.socialapp.presentation.post.postDetails.PostDetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.gp.socialapp.util.Result

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
            replyRepo.getReplies(id).collect { replies ->
//               _uiState.update { it.copy(currentReplies = replies) }
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
                    TODO("Handle error here")
                }
                is Result.Loading -> {
                    //TODO Handle loading here
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
                    //TODO Handle error here
                }
                is Result.Loading -> {
                    //TODO Handle loading here
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
                    //TODO Handle error here
                }
                is Result.Loading -> {
                    //TODO Handle loading here
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
                    //TODO Handle success here
                }
                is Result.Error -> {
                    //TODO Handle error here
                }
                is Result.Loading -> {
                    //TODO Handle loading here
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
                }
                is Result.Error -> {
                    //TODO Handle error here
                }
                is Result.Loading -> {
                    //TODO Handle loading here
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
                    //TODO Handle error here
                }
                is Result.Loading -> {
                    //TODO Handle loading here
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
                    //TODO Handle error here
                }
                is Result.Loading -> {
                    //TODO Handle loading here
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
                }
                is Result.Error -> {
                    //TODO Handle error here
                }
                is Result.Loading -> {
                    //TODO Handle loading here
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
                }
                is Result.Error -> {
                    //TODO Handle error here
                }
                is Result.Loading -> {
                    //TODO Handle loading here
                }
                else -> Unit
            }
        }
    }

    fun handlePostEvent(event: PostEvent) {
        when (event) {
            is PostEvent.OnPostDeleted -> deletePost(event.post)
            is PostEvent.OnPostUpVoted -> upvotePost(event.post)
            is PostEvent.OnPostDownVoted -> downvotePost(event.post)
            is PostEvent.onCommentAdded -> {
                val reply = Reply(
                    postId = event.postId,
                    parentReplyId = null,
                    depth = 0,
                    content = event.text,
                    authorID = _uiState.value.currentUser.id,
                )
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
                insertReply(reply)
            }

            else -> {}
        }
    }
}