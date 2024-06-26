package com.gp.socialapp.presentation.assignment.submissions_screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.presentation.assignment.submissionreview.SubmissionReviewScreen
import com.gp.socialapp.util.LocalDateTimeUtil.convertEpochToTime
import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.awt.SystemColor.text

data class SubmissionsScreen(
    val assignment: Assignment,
) : Screen {


    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<SubmissionsScreenModel>()
        LifecycleEffect(
            onStarted = {
                screenModel.init(assignment)
            }
        )
        val state = screenModel.uiState.collectAsState()
        SubmissionsScreenContent(
            state = state.value,
            onBack = {
                navigator.pop()
            },
            action = {
                when (it) {
                    is SubmissionsScreenUiAction.SubmissionClick -> {
                        navigator.push(SubmissionReviewScreen(assignment.id, it.submission.id))
                    }
                }
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmissionsScreenContent(
    state: SubmissionsScreenUiState,
    onBack: () -> Unit,
    action: (SubmissionsScreenUiAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = " Student Submissions") },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Image(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            Row(
                Modifier.fillMaxWidth().padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 4.dp,
                    bottom = 4.dp
                ).padding(8.dp)
            ) {
                Text(
                    text = "Student",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Status",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Submit in",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Grade",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp, horizontal = 8.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                thickness = 2.dp
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.submissions) { submission ->
                    val animatable = remember { Animatable(0.5f) }
                    LaunchedEffect(key1 = true) {
                        animatable.animateTo(1f, tween(350, easing = FastOutSlowInEasing))
                    }
                    SubmissionItem(
                        modifier = Modifier.graphicsLayer {
                            this.scaleX = animatable.value
                            this.scaleY = animatable.value
                        },
                        submission,
                        action = action
                    )
                }
            }
        }
    }
}

@Composable
fun SubmissionItem(
    modifier: Modifier = Modifier,
    submission: UserAssignmentSubmission,
    action: (SubmissionsScreenUiAction) -> Unit
) {
    OutlinedCard(
        onClick = { action(SubmissionsScreenUiAction.SubmissionClick(submission)) },
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            ),

    ) {
        Row(
            modifier = modifier
                .fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val status = when  {
                submission.isReviewed -> "Reviewed"
                else -> "Not Reviewed"
            }
            val stateColor = when {
                submission.isReviewed -> Color.Green
                else -> Color.Red
            }
            val submitTime = ((LocalDateTime.now().toInstant(TimeZone.UTC).toEpochMilliseconds()-submission.submittedAt)/ 1000 / 60 / 60 / 24).toString() + " days"
            Text(
                text = submission.userName,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
            Text(
                text = status,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                textAlign = TextAlign.Center,
                color = stateColor,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = submitTime,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = submission.grade.toString(),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}