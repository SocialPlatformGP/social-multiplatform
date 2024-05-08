package com.gp.socialapp.presentation.calendar.createevent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.calendar.createevent.components.CreateEventDate
import com.gp.socialapp.presentation.calendar.createevent.components.CreateEventDescription
import com.gp.socialapp.presentation.calendar.createevent.components.CreateEventTitle

object CreateEventScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<CreateEventScreenModel>()
        val state by screenModel.uiState.collectAsState()
        if(state.isCreated){
            navigator.pop()
        }
        LifecycleEffect(
            onStarted = {
                screenModel.init()
            },
            onDisposed = {
                screenModel.onDispose()
            }
        )
        CreateCalendarContent {
            when(it){
                is CreateCalendarUiAction.BackPressed -> {
                    navigator.pop()
                }
                else -> {
                    screenModel.handleUiAction(it)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateCalendarContent(
        modifier: Modifier = Modifier,
        onAction: (CreateCalendarUiAction) -> Unit,
    ) {
        Scaffold (
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Create Event",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.weight(1f)
                            )
                            Button(
                                onClick = {
                                    onAction(CreateCalendarUiAction.CreateEvent)
                                }
                            ) {
                                Text(text = "Create")
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onAction(CreateCalendarUiAction.BackPressed)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBackIosNew,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) {  paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)
            ) {
                CreateEventTitle {
                    onAction(CreateCalendarUiAction.EventTitleChanged(it))
                }
                CreateEventDescription {
                    onAction(CreateCalendarUiAction.EventDescriptionChanged(it))
                }
                CreateEventDate {
                    onAction(CreateCalendarUiAction.EventDateChanged(it))
                }
            }

        }
    }
}