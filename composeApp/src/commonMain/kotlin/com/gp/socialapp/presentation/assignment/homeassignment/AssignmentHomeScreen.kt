package com.gp.socialapp.presentation.assignment.homeassignment

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
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.gp.socialapp.presentation.assignment.submitassignment.SubmitAssignmentScreen
import com.gp.socialapp.util.LocalDateTimeUtil.convertEpochToTime

data class AssignmentHomeScreen(val communityId: String = "") : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<AssignmentHomeScreenModel>()
        val state = screenModel.uiState.collectAsState()
        LifecycleEffect(
            onStarted = {
                screenModel.onInit(communityId)
            },
        )
        AssignmentHomeScreenContent(
            state = state.value,
            action = fun(action: AssignmentHomeUiAction) {
                when (action) {
                    is AssignmentHomeUiAction.OnAssignmentClicked -> {
                        if (action.assignment.creatorId == state.value.currentUser.id) {
                            //TODO Navigate to dashboard
                        } else {
                            navigator.push(
                                SubmitAssignmentScreen(action.assignment)
                            )
                        }
                    }

                    AssignmentHomeUiAction.OnBackClicked -> navigator.pop()
                }
            }
        )
    }
}

@Composable
fun AssignmentHomeScreenContent(
    state: AssignmentHomeUiState, action: (AssignmentHomeUiAction) -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.assignments) { assignment ->
                    val animatable = remember { Animatable(0.5f) }
                    LaunchedEffect(key1 = true) {
                        animatable.animateTo(1f, tween(350, easing = FastOutSlowInEasing))
                    }
                    AssignmentItem(
                        modifier = Modifier.graphicsLayer {
                            this.scaleX = animatable.value
                            this.scaleY = animatable.value
                        },
                        assignment,
                        action = action
                    )
                }
            }
        }
    }
}

@Composable
fun AssignmentItem(
    modifier: Modifier = Modifier,
    assignment: Assignment,
    action: (AssignmentHomeUiAction) -> Unit
) {
    Card(
        onClick = { action(AssignmentHomeUiAction.OnAssignmentClicked(assignment)) },
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
                        text = assignment.title
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = convertEpochToTime(assignment.createdAt),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

            }
        }
    }


}