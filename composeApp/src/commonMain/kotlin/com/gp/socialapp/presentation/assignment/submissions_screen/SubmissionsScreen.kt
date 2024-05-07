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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.data.assignment.model.UserAssignmentSubmission
import com.gp.socialapp.presentation.assignment.homeassignment.AssignmentHomeUiAction
import com.gp.socialapp.presentation.assignment.homeassignment.AssignmentHomeUiState
import com.gp.socialapp.presentation.assignment.homeassignment.AssignmentItem
import com.gp.socialapp.presentation.assignment.submissionreview.SubmissionReviewScreen
import com.gp.socialapp.util.LocalDateTimeUtil.convertEpochToTime

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
    action: (SubmissionsScreenUiAction) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Submissions") },
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
    ){ paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
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
    Card(
        onClick = { action(SubmissionsScreenUiAction.SubmissionClick(submission)) },
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.AutoMirrored.Filled.Assignment,
                contentDescription = "Assignment",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp).padding(4.dp).clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 8.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = submission.userName
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = convertEpochToTime(submission.submittedAt),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

            }
        }
    }
}