package com.gp.socialapp.presentation.calendar.createevent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.calendar.createevent.components.CreateEventDate
import com.gp.socialapp.presentation.calendar.createevent.components.CreateEventDescription
import com.gp.socialapp.presentation.calendar.createevent.components.CreateEventTime
import com.gp.socialapp.presentation.calendar.createevent.components.CreateEventTitle

data class CreateEventScreen(val communityId: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<CreateEventScreenModel>()
        val state by screenModel.uiState.collectAsState()
        if (state.isCreated) {
            navigator.pop()
        }
        LifecycleEffect(onStarted = {
            screenModel.init(communityId)
        }, onDisposed = {
            screenModel.onDispose()
        })
        CreateCalendarContent(currentUser = state.currentUser, state = state) {
            when (it) {
                is CreateCalendarUiAction.BackPressed -> {
                    navigator.pop()
                }

                else -> {
                    screenModel.handleUiAction(it)
                }
            }
        }
    }

    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
        ExperimentalMaterial3WindowSizeClassApi::class
    )
    @Composable
    fun CreateCalendarContent(
        modifier: Modifier = Modifier,
        currentUser: User,
        state: CreateEventUiState,
        onAction: (CreateCalendarUiAction) -> Unit,

        ) {
        val windowSize = calculateWindowSizeClass()
        var compact by remember { mutableStateOf(false) }
        when (windowSize.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                compact = true
            }

            else -> {
                compact = false
            }
        }
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Create Event",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }, navigationIcon = {
                    IconButton(onClick = {
                        onAction(CreateCalendarUiAction.BackPressed)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                })
            }) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(
                    rememberScrollState()
                ).padding(paddingValues).padding(16.dp)
            ) {
                if (compact) {
                    CreateEventTitle {
                        onAction(CreateCalendarUiAction.EventTitleChanged(it))
                    }
                    CreateEventDescription {
                        onAction(CreateCalendarUiAction.EventDescriptionChanged(it))
                    }
                }
                else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CreateEventTitle(
                            modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
                        ) {
                            onAction(CreateCalendarUiAction.EventTitleChanged(it))
                        }
                        CreateEventDescription(
                            modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
                        ) {
                            onAction(CreateCalendarUiAction.EventDescriptionChanged(it))
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))
                if (compact) {
                    CreateEventDate(
                        onDateChanged = {
                            onAction(
                                CreateCalendarUiAction.EventDateChanged(
                                    it
                                )
                            )
                        }
                    )
                    CreateEventTime{
                        onAction(CreateCalendarUiAction.EventTimeChanged(it))
                    }
                }
                else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CreateEventDate(
                            modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                            onDateChanged = {
                                onAction(
                                    CreateCalendarUiAction.EventDateChanged(
                                        it
                                    )
                                )
                            }
                        )
                        CreateEventTime(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                            onAction(CreateCalendarUiAction.EventTimeChanged(it))
                        }
                    }
                }
                //todo reverse it to be visible
                if (!currentUser.isAdmin) {
                    FlowRow (
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        Row(
                            modifier = Modifier.clip(CircleShape).padding(8.dp).background(MaterialTheme.colorScheme.surfaceVariant).padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var online by remember { mutableStateOf(true) }
                            Text(
                                text = "Offline",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                            Spacer(modifier = Modifier.padding(8.dp))

                            Switch(
                                checked = online,
                                onCheckedChange = {
                                    online = it
                                    onAction(CreateCalendarUiAction.EventLocationChanged(if (it) "Online" else "Offline"))
                                },
                            )
                            Spacer(modifier = Modifier.padding(8.dp))

                            Text(
                                text = "Online",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }
                        Row(
                            modifier = Modifier.clip(CircleShape).padding(8.dp).background(MaterialTheme.colorScheme.surfaceVariant).padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var type by remember { mutableStateOf(false) }
                            Text(
                                text = "Event",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            Switch(
                                checked = type, onCheckedChange = {
                                    type = it
                                    onAction(CreateCalendarUiAction.EventTypeChanged(if (it) "Lecture" else "Event"))
                                },
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(
                                text = "Lecture",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }
                        Row(
                            modifier = Modifier.clip(CircleShape).padding(8.dp).background(MaterialTheme.colorScheme.surfaceVariant).padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var visible by remember { mutableStateOf(false) }
                            Text(
                                text = "Me only",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            Switch(
                                checked = visible, onCheckedChange = {
                                    visible = it
                                    onAction(CreateCalendarUiAction.EventAssignToChanged(visible))
                                }

                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(
                                text = "Everyone",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onAction(CreateCalendarUiAction.CreateEvent)
                        },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        enabled = state.title != ""
                    ) {
                        Text(text = "Create New ${state.type}")
                    }
                }

            }

        }
    }
}