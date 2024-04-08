package com.gp.socialapp.presentation.post.postDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.post.source.remote.model.NestedReply
import com.gp.socialapp.data.post.source.remote.model.Post
import com.gp.socialapp.data.post.source.remote.model.Reply
import com.gp.socialapp.presentatDefaultn.post.postDetails.PostDetailsScreenModel
import com.gp.socialapp.presentation.post.feed.PostEvent
import com.gp.socialapp.presentation.post.feed.ReplyEvent
import com.gp.socialapp.presentation.post.feed.components.FeedPostItem
import com.gp.socialapp.presentation.post.postDetails.components.AddReplySheet
import com.gp.socialapp.presentation.post.postDetails.components.RepliesList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.confirm
import socialmultiplatform.composeapp.generated.resources.confirm_report_reply
import socialmultiplatform.composeapp.generated.resources.dismiss
import socialmultiplatform.composeapp.generated.resources.post_delete_completed
import socialmultiplatform.composeapp.generated.resources.post_report_completed
import socialmultiplatform.composeapp.generated.resources.post_update_completed
import socialmultiplatform.composeapp.generated.resources.reply_delete_completed
import socialmultiplatform.composeapp.generated.resources.reply_report_completed
import socialmultiplatform.composeapp.generated.resources.reply_update_completed
import socialmultiplatform.composeapp.generated.resources.report_reply
import socialmultiplatform.composeapp.generated.resources.unknown_action_result

data class PostDetailsScreen(val post: Post) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<PostDetailsScreenModel>()
        screenModel.initScreenModel(post)
        val state by screenModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        var clickedReply by remember { mutableStateOf<Reply?>(null) }
        var isReportDialogVisible by remember { mutableStateOf(false) }
        val bottomSheetState = rememberModalBottomSheetState()
        PostDetailsContent(
            replies = state.currentReplies,
            onPostEvent = { postEvent ->
                when (postEvent) {
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
                when (replyEvent) {
                    is ReplyEvent.OnReportReply -> {
                        isReportDialogVisible = true
                        clickedReply = replyEvent.reply
                    }

                    is ReplyEvent.OnAddReply -> {
                        scope.launch {
                            if (bottomSheetState.isVisible) {
                                bottomSheetState.hide()
                            } else {
                                bottomSheetState.show()
                                clickedReply = replyEvent.reply
                            }
                        }
                    }

                    else -> screenModel.handleReplyEvent(replyEvent)
                }
            },
            currentUserID = state.currentUser.id,
            clickedReply = clickedReply,
            bottomSheetState = bottomSheetState,
            onDismissAddReplyBottomSheet = {
                scope.launch {
                    bottomSheetState.hide()
                }
            },
            isReportDialogVisible = isReportDialogVisible,
            onDismissReportDialog = {
                isReportDialogVisible = false
            },
            onConfirmReport = {
                isReportDialogVisible = false
                screenModel.handleReplyEvent(ReplyEvent.OnReplyReported(reply = clickedReply!!))
            },
            onResetActionResult = screenModel::resetActionResult
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun PostDetailsContent(
        modifier: Modifier = Modifier,
        replies: NestedReply,
        onPostEvent: (PostEvent) -> Unit,
        onReplyEvent: (ReplyEvent) -> Unit,
        currentUserID: String,
        actionResult: PostDetailsActionResult = PostDetailsActionResult.NoActionResult,
        scope: CoroutineScope = rememberCoroutineScope(),
        onResetActionResult: () -> Unit,
        clickedReply: Reply?,
        bottomSheetState: SheetState,
        isReportDialogVisible: Boolean = false,
        onDismissReportDialog: () -> Unit,
        onConfirmReport: () -> Unit,
        onDismissAddReplyBottomSheet: () -> Unit,
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, modifier = modifier
        ) {
            if (actionResult !is PostDetailsActionResult.NoActionResult) {
                val message = when (actionResult) {
                    is PostDetailsActionResult.NetworkError -> actionResult.message
                    is PostDetailsActionResult.ReplyDeleted -> stringResource(resource = Res.string.reply_delete_completed)
                    is PostDetailsActionResult.ReplyUpdated -> stringResource(resource = Res.string.reply_update_completed)
                    is PostDetailsActionResult.ReplyReported -> stringResource(resource = Res.string.reply_report_completed)
                    is PostDetailsActionResult.PostDeleted -> stringResource(resource = Res.string.post_delete_completed)
                    is PostDetailsActionResult.PostReported -> stringResource(resource = Res.string.post_report_completed)
                    is PostDetailsActionResult.PostUpdated -> stringResource(resource = Res.string.post_update_completed)
                    else -> stringResource(resource = Res.string.unknown_action_result)
                }
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = message
                    )
                    delay(1500)
                    onResetActionResult()
                }
            }
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
            ) {
                LazyColumn(
                    modifier = Modifier.systemBarsPadding().fillMaxSize()
                ) {
                    item {
                        FeedPostItem(
                            post = post, onPostEvent = onPostEvent, currentUserID = currentUserID
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                    RepliesList(
                        replies = listOf(replies),
                        onReplyEvent = onReplyEvent,
                        currentUserId = currentUserID
                    )
                }
                if (isReportDialogVisible) {
                    AlertDialog(title = {
                        Text(text = stringResource(resource = Res.string.report_reply))
                    }, text = {
                        Text(text = stringResource(resource = Res.string.confirm_report_reply))
                    }, onDismissRequest = {
                        onDismissReportDialog()
                    }, confirmButton = {
                        TextButton(onClick = {
                            onConfirmReport()
                        }) {
                            Text(text = stringResource(resource = Res.string.confirm))
                        }
                    }, dismissButton = {
                        TextButton(onClick = {
                            onDismissReportDialog()
                        }) {
                            Text(text = stringResource(resource = Res.string.dismiss))
                        }
                    })
                }
                if (bottomSheetState.isVisible) {
                    AddReplySheet(
                        onDismiss = onDismissAddReplyBottomSheet, onDone = { reply ->
                            onReplyEvent(ReplyEvent.OnAddReply(clickedReply!!))
                            onReplyEvent(ReplyEvent.OnReplyAdded(reply, clickedReply))
                            onDismissAddReplyBottomSheet()
                        }, bottomSheetState = bottomSheetState
                    )
                }
            }
        }
    }
}
