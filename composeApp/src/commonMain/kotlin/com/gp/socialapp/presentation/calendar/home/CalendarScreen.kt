package com.gp.socialapp.presentation.calendar.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.calendar.model.CalendarEvent
import com.gp.socialapp.presentation.calendar.home.components.CalendarWithEvents

object CalendarHomeScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<CalendarHomeScreenModel>()
        val state = screenModel.uiState.collectAsState()
        CalendarScreenContent(
            events = state.value.events,
            onAction = { action ->
                when(action) {
                    is CalendarHomeUiAction.CreateEvent -> {
//                        navigator.push()
                        TODO()
                    }
                }
            }
        )
    }

    @Composable
    fun CalendarScreenContent(
        modifier: Modifier = Modifier,
        events: List<CalendarEvent>,
        onAction: (CalendarHomeUiAction) -> Unit,
    ) {
        Scaffold (
            modifier = modifier.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onAction(CalendarHomeUiAction.CreateEvent)
                    }
                ){
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            }
        ) { paddingValues ->
            Column (
                modifier = Modifier.fillMaxSize().padding(16.dp).padding(paddingValues)
            ) {
                CalendarWithEvents(
                    events = events,
                )
            }
        }
    }
}