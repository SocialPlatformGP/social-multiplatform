package com.gp.socialapp.presentation.assignment.homeassignment

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.assignment.model.Assignment
import com.gp.socialapp.presentation.assignment.createassignment.CreateAssignmentScreen
import com.gp.socialapp.presentation.assignment.submissions_screen.SubmissionsScreen
import com.gp.socialapp.presentation.assignment.submitassignment.SubmitAssignmentScreen
import com.gp.socialapp.util.LocalDateTimeUtil.toLocalDateTime

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
                            navigator.push(SubmissionsScreen(action.assignment))
                        } else {
                            navigator.push(
                                SubmitAssignmentScreen(action.assignment)
                            )
                        }
                    }

                    AssignmentHomeUiAction.OnBackClicked -> navigator.pop()
                    AssignmentHomeUiAction.OnFabClicked -> {
                        navigator.push(CreateAssignmentScreen(communityId))
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AssignmentHomeScreenContent(
    state: AssignmentHomeUiState, action: (AssignmentHomeUiAction) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    action(AssignmentHomeUiAction.OnFabClicked)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            val windowSize = calculateWindowSizeClass()
            var compact: Boolean by remember {
                mutableStateOf(false)
            }
            when (windowSize.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    compact = true
                }

                else -> {
                    compact = false
                }
            }
            println("Compact?: $compact")
            Row(
                Modifier.fillMaxWidth().padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 4.dp,
                    bottom = 4.dp
                ).padding(8.dp)
            ) {
                Text(
                    text = "Title",
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Status",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                if (!compact) {
                    Text(
                        text = "Created at",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
                Text(
                    text = "Due Date",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                if (!compact) {
                    Text(
                        text = "Duration",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp, horizontal = 8.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                thickness = 2.dp
            )
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
                        action = action,
                        isSizeCompact = compact
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
    action: (AssignmentHomeUiAction) -> Unit,
    isSizeCompact: Boolean
) {
    OutlinedCard(
        onClick = { action(AssignmentHomeUiAction.OnAssignmentClicked(assignment)) },
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            ),

        ) {
        println("Compact?: $isSizeCompact")

        val statusText = when (assignment.dueDate) {
            in Long.MIN_VALUE..System.currentTimeMillis() -> "Closed"
            else -> "Open"
        }
        val statusColor = when (assignment.dueDate) {
            in Long.MIN_VALUE..System.currentTimeMillis() -> Color.Red
            else -> Color.Green
        }
        Row(
            modifier = modifier
                .fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val createdAt = assignment.createdAt.toLocalDateTime()
            val dueDate = assignment.dueDate.toLocalDateTime()
            Text(
                text = assignment.title,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Start
            )
            Text(
                text = statusText,
                color = statusColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            if (!isSizeCompact) {
                Text(
                    text = createdAt.dayOfMonth.toString() + " - " + createdAt.month.toString()
                        .toLowerCase().replaceFirstChar { it.uppercase() },
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = dueDate.dayOfMonth.toString() + " - " + dueDate.month.toString()
                    .toLowerCase().replaceFirstChar { it.uppercase() },
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            if (!isSizeCompact) {
                Text(
                    text = ((assignment.dueDate - assignment.createdAt) / 1000 / 60 / 60 / 24).toString() + " days",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}