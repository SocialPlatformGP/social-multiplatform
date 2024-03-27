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
            replyRepo.insertReply(reply)
        }
    }

    private fun upVote(post: Post) {
        screenModelScope.launch(Dispatchers.Default) {
            postRepo.upvotePost(post)
        }
    }

    private fun downVote(post: Post) {
        screenModelScope.launch(Dispatchers.Default) {
            postRepo.downvotePost(post)
        }
    }

    private fun deletePost(post: Post) {
        screenModelScope.launch(Dispatchers.Default) {
            postRepo.deletePost(post)
        }
    }

    private fun updatePost(post: Post) {
        screenModelScope.launch(Dispatchers.Default) {
            postRepo.updatePost(post)
        }
    }

    private fun replyUpVote(reply: Reply) {
        screenModelScope.launch(Dispatchers.Default) {
            replyRepo.upVoteReply(reply)
        }
    }

    private fun replyDownVote(reply: Reply) {
        screenModelScope.launch(Dispatchers.Default) {
            replyRepo.downVoteReply(reply)
        }
    }

    private fun deleteReply(reply: Reply) {
        screenModelScope.launch(Dispatchers.Default) {
            var deletedReply = reply.copy(content = "content is deleted by owner", deleted = true)
            replyRepo.deleteReply(deletedReply)
        }

    }

    private fun updateReply(reply: Reply) {
        screenModelScope.launch(Dispatchers.Default) {
            replyRepo.updateReply(reply)
        }
    }

    fun handlePostEvent(event: PostEvent) {
        when (event) {
            is PostEvent.OnPostDeleted -> deletePost(event.post)
            is PostEvent.OnPostUpVoted -> upVote(event.post)
            is PostEvent.OnPostDownVoted -> downVote(event.post)
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
            is ReplyEvent.OnReplyUpVoted -> replyUpVote(event.reply)
            is ReplyEvent.OnReplyDownVoted -> replyDownVote(event.reply)

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