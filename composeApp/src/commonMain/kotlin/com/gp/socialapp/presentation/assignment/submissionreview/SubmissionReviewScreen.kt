package com.gp.socialapp.presentation.assignment.submissionreview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.presentation.assignment.submissionreview.components.SubmissionAttachmentPreview
import com.gp.socialapp.presentation.assignment.submissionreview.components.SubmissionReviewSideColumn
import com.gp.socialapp.presentation.assignment.submissionreview.components.SubmissionReviewTopRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class SubmissionReviewScreen(
    private val assignmentId: String,
    private val submissionId: String,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<SubmissionReviewScreenModel>()
        val uiState by screenModel.uiState.collectAsState()
        LifecycleEffect(onStarted = { screenModel.init(assignmentId, submissionId) },
            onDisposed = { screenModel.onDispose() })
        SubmissionReviewContent(
            onAction = { action ->
                when (action) {
                    is SubmissionReviewUiAction.BackPressed -> navigator.pop()
                    else -> screenModel.submitAction(action)
                }
            },
            assignmentTitle = uiState.currentAssignment.title,
            maxAssignmentGrade = uiState.currentAssignment.maxPoints,
            submissions = uiState.submissions,
            currentPreviewedAttachmentId = uiState.currentPreviewedAttachmentId
        )
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    private fun SubmissionReviewContent(
        modifier: Modifier = Modifier,
        onAction: (action: SubmissionReviewUiAction) -> Unit,
        assignmentTitle: String,
        maxAssignmentGrade: Int,
        submissions: List<UserAssignmentSubmission>,
        currentPreviewedAttachmentId: String,
        scope: CoroutineScope = rememberCoroutineScope()
    ) {
        val pagerState = rememberPagerState(
            initialPage = submissions.indexOfFirst { it.id == submissionId }.let { if (it == -1) 0 else it },
            pageCount = { submissions.size },
        )
        val currentSubmission = submissions[pagerState.currentPage]
        Scaffold(modifier = modifier, topBar = {
            TopAppBar(title = {
                Text(
                    text = assignmentTitle,
                    maxLines = 1,
                    style = MaterialTheme.typography.headlineSmall
                )
            }, navigationIcon = {
                IconButton(onClick = { onAction(SubmissionReviewUiAction.BackPressed) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back"
                    )
                }
            })
        }) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                //Top Row (newt, previous , user dropdown menu, submit grade)
                SubmissionReviewTopRow(currentSubmission = currentSubmission,
                    submissions = submissions,
                    onSubmitReviewClicked = {
                        onAction(SubmissionReviewUiAction.SubmitReview)
                    },
                    onSubmissionSelected = { submissionId ->
                        scope.launch {
                            pagerState.animateScrollToPage(submissions.indexOfFirst { it.id == submissionId }
                                .let { if (it == -1) pagerState.currentPage else it })
                        }
                        onAction(SubmissionReviewUiAction.ViewSubmission(submissionId))
                    },
                    onPreviousClicked = {
                        scope.launch {
                            if (pagerState.currentPage > 0) {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                        onAction(SubmissionReviewUiAction.ViewPrevious(submissions[pagerState.currentPage - 1].id))
                    },
                    onNextClicked = {
                        scope.launch {
                            if (pagerState.currentPage < submissions.size - 1) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                        onAction(SubmissionReviewUiAction.ViewNext(submissions[pagerState.currentPage + 1].id))
                    })
                HorizontalPager(
                    state = pagerState, modifier = Modifier.fillMaxSize()
                ) { page ->
                    //Row(Attachment Preview, Side Column)
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        //AttachmentPreview
                        SubmissionAttachmentPreview(modifier = modifier.weight(1f),
                            attachment = currentSubmission.attachments.find { it.id == currentPreviewedAttachmentId })
                        //Side Column (Submission Details, Comments, Grade, Feedback)
                        SubmissionReviewSideColumn(currentSubmission = currentSubmission,
                            currentPreviewedAttachmentId = currentPreviewedAttachmentId,
                            maxAssignmentGrade = maxAssignmentGrade,
                            onAttachmentClicked = { attachmentId ->
                                onAction(SubmissionReviewUiAction.AttachmentClicked(attachmentId))
                            },
                            onGradeChanged = { grade ->
                                onAction(SubmissionReviewUiAction.GradeChanged(grade))
                            },
                            onFeedbackChanged = { feedback ->
                                onAction(SubmissionReviewUiAction.FeedbackChanged(feedback))
                            })
                    }
                }
            }
        }
    }

}
