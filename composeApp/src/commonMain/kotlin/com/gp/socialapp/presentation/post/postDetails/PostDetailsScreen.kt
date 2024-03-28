package com.gp.socialapp.presentation.post.postDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.post.source.remote.model.NestedReply
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.presentatDefaultn.post.postDetails.PostDetailsScreenModel
import com.gp.socialapp.presentation.post.feed.PostEvent
import com.gp.socialapp.presentation.post.feed.ReplyEvent
import com.gp.socialapp.presentation.post.feed.components.FeedPostItem
import com.gp.socialapp.presentation.post.postDetails.components.AddReplySheet
import com.gp.socialapp.presentation.post.postDetails.components.RepliesList
import kotlinx.coroutines.launch

data class PostDetailsScreen(val post: Post): Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<PostDetailsScreenModel>()
        screenModel.initScreenModel(post)
        val state by screenModel.uiState.collectAsState()
        val keyboardController = LocalSoftwareKeyboardController.current
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState()
        PostDetailsContent(
            replies = state.currentReplies,
            onPostEvent = { postEvent ->
                when(postEvent){
                    is PostEvent.OnCommentClicked -> {
                        scope.launch {
                            if (bottomSheetState.isVisible) {
                                bottomSheetState.hide()
                            } else {
                                bottomSheetState.show()
                            }
                        }
                    }
                    else -> screenModel.handlePostEvent(postEvent)
                }
            },
            onReplyEvent = { replyEvent ->
                when(replyEvent){
                    is ReplyEvent.OnAddReply -> {
                        scope.launch {
                            if (bottomSheetState.isVisible) {
                                bottomSheetState.hide()
                            } else {
                                bottomSheetState.show()
                            }
                        }
                    }
                    else -> screenModel.handleReplyEvent(replyEvent)
                }
            },
            currentUserID = "",/*TODO add actual user id*/
            bottomSheetState = bottomSheetState,
            onDismissAddReplyBottomSheet = {
                scope.launch {
                    bottomSheetState.hide()
                }
            },
            onAddReply = { reply ->
//                screenModel.insertReply(
//                    NestedReply(
//                        id = "",
//                        postID = post.id,
//                        userID = state.currentUserID,
//                        content = reply,
//                        replies = emptyList()
//                    )
//                )
//                screenModel.setAddReplyBottomSheetVisibility(false)
//                keyboardController?.hide()
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun PostDetailsContent(
        modifier: Modifier =Modifier,
        replies: List<NestedReply>,
        onPostEvent: (PostEvent) -> Unit,
        onReplyEvent: (ReplyEvent) -> Unit,
        currentUserID: String,
        bottomSheetState: SheetState,
        onDismissAddReplyBottomSheet: () -> Unit,
        onAddReply: (String) -> Unit
    ) {
        Box(
            modifier = modifier
                .fillMaxSize(),
        ) {
            LazyColumn (
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxSize()
            ) {
                item {
                    FeedPostItem(
                        post = post,
                        onPostEvent = onPostEvent,
                        currentUserID = currentUserID
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
                RepliesList(
                    replies = replies,
                    onReplyEvent = onReplyEvent
                )
            }
            if(bottomSheetState.isVisible){
                AddReplySheet(
                    onDismiss = onDismissAddReplyBottomSheet,
                    onDone = onAddReply,
                    bottomSheetState = bottomSheetState
                )
            }
        }
    }
}
