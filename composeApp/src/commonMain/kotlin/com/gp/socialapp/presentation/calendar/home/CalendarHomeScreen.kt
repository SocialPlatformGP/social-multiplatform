package com.gp.socialapp.presentation.calendar.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.calendar.model.CalendarEvent
import com.gp.socialapp.presentation.calendar.createevent.CreateEventScreen
import com.gp.socialapp.util.LocalDateTimeUtil.convertEpochToTime
import com.gp.socialapp.util.LocalDateTimeUtil.toLocalDateTime
import kotlinx.coroutines.launch

data class CalendarHomeScreen(val communityId: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<CalendarHomeScreenModel>()
        LifecycleEffect(
            onStarted = { screenModel.init() },
            onDisposed = { screenModel.onDispose() }
        )
        val state = screenModel.uiState.collectAsState()
        CalendarScreenContent(
            events = state.value.events,
            onAction = { action ->
                when (action) {
                    is CalendarHomeUiAction.CreateEvent -> {
                        navigator.push(
                            CreateEventScreen(
                                communityId
                            )
                        )
                    }
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    fun CalendarScreenContent(
        modifier: Modifier = Modifier,
        events: List<CalendarEvent>,
        onAction: (CalendarHomeUiAction) -> Unit,
    ) {
        val windowSize = calculateWindowSizeClass()
        var compact by remember { mutableStateOf(false) }
        when (windowSize.widthSizeClass) {
            WindowWidthSizeClass.Compact -> compact = true
            WindowWidthSizeClass.Medium -> compact = false
            else -> {
                compact = false
            }
        }
        Scaffold(
            modifier = modifier.fillMaxSize(),
            floatingActionButton = {
                if (compact) {
                    FloatingActionButton(
                        onClick = {
                            onAction(CalendarHomeUiAction.CreateEvent)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null
                        )
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp).padding(paddingValues)
            ) {
                CalendarWithEvents(
                    events = events,
                    action = onAction
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CalendarWithEvents(
    events: List<CalendarEvent>,
    action: (CalendarHomeUiAction) -> Unit
) {

    val windowSize = calculateWindowSizeClass()
    var compact by remember { mutableStateOf(false) }
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> compact = true
        else -> {
            compact = false
        }
    }
    val tabItems = listOf(
        "All",
        "Tasks",
        "Projects",
        "Lectures",
        "Events"
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { tabItems.size }
    val scope = rememberCoroutineScope()
    Column(
        Modifier.fillMaxSize()
    ) {

        if (windowSize.widthSizeClass != WindowWidthSizeClass.Compact) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        height = 0.dp
                    )
                },
                modifier = Modifier.clip(RoundedCornerShape(4.dp)).heightIn(max = 52.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                tabItems.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = (index == selectedTabIndex),
                        modifier =
                        if (index == selectedTabIndex)
                            Modifier
                                .heightIn(max = 40.dp)
                                .padding(6.dp)
                                .background(
                                    MaterialTheme.colorScheme.onPrimaryContainer,
                                    RoundedCornerShape(4.dp)
                                )
                        else Modifier
                            .heightIn(max = 40.dp),
                        onClick = {
                            selectedTabIndex = index
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = tabItem,
                                color = if (index == selectedTabIndex) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = 14.sp
                            )
                        }

                    )
                }
            }
        } else {
            LazyRow {
                itemsIndexed(tabItems) { index, tabItem ->
                    Tab(
                        selected = (index == selectedTabIndex),
                        modifier =
                        if (index == selectedTabIndex)
                            Modifier
                                .heightIn(max = 40.dp)
                                .padding(6.dp)
                                .background(
                                    MaterialTheme.colorScheme.onPrimaryContainer,
                                    RoundedCornerShape(4.dp)
                                )
                        else Modifier
                            .heightIn(max = 40.dp),
                        onClick = {
                            selectedTabIndex = index
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = tabItem,
                                color = if (index == selectedTabIndex) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = 14.sp
                            )
                        }

                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        if (!compact) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(
                        onClick = { action(CalendarHomeUiAction.CreateEvent) },
                        modifier = Modifier
                            .padding(horizontal = 6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                        shape = RoundedCornerShape(4.dp)

                    ) {
                        Text(text = "Create Event")
                    }
                }
            }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            when (index) {
                0 -> {
                    CalendarEventsList(events = events, action = action)
                }

                1 -> {
                    CalendarEventsList(
                        events = events.filter { it.type == EventType.TASK.value },
                        action = action
                    )
                }

                2 -> {
                    CalendarEventsList(
                        events = events.filter { it.type == EventType.PROJECT.value },
                        action = action
                    )
                }

                3 -> {
                    CalendarEventsList(
                        events = events.filter { it.type == EventType.LECTURE.value },
                        action = action
                    )
                }

                4 -> {
                    CalendarEventsList(
                        events = events.filter { it.type == EventType.EVENT.value },
                        action = action
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CalendarEventsList(events: List<CalendarEvent>, action: (CalendarHomeUiAction) -> Unit) {

    val monthesInEvents = events.map { it.date.toLocalDateTime().month }.toSet()
    LazyColumn {

        monthesInEvents.forEach { month ->
            stickyHeader {
                Text(
                    text = month.toString().lowercase().replaceFirstChar { it.uppercase() },
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 4.dp)

                )
            }
            items(events.filter { it.date.toLocalDateTime().month == month }) { event ->
                CalendarEventItem(event = event)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CalendarEventItem(event: CalendarEvent) {
    val windowSize = calculateWindowSizeClass()
    var compact by remember { mutableStateOf(false) }
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> compact = true
        WindowWidthSizeClass.Medium -> compact = false
        else -> {
            compact = false
        }
    }
    Card(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
                .height(IntrinsicSize.Min)
        ) {
            Column(
                modifier = Modifier.padding(8.dp).fillMaxHeight().weight(if (compact) 1f else 0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = event.date.toLocalDateTime().dayOfWeek.toString().substring(0, 3)
                        .lowercase().replaceFirstChar { it.uppercase() },
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(2.dp),
                    style = if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) MaterialTheme.typography.bodySmall else MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = event.date.toLocalDateTime().dayOfMonth.toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            VerticalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = Color.DarkGray
            )
            Column(
                modifier = Modifier.padding(horizontal = 8.dp).fillMaxHeight()
                    .weight(if (compact) 3f else 1f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                Row {
                    Icon(
                        Icons.Filled.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = convertEpochToTime(event.time),
                    )
                }
                Row {
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = event.location,
                    )
                }
                if (compact) {
                    Text(
                        text = event.title,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = event.description,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = event.user,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }

            }
            if (!compact) {
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp).fillMaxHeight().weight(1f),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = event.title,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = event.description,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp).fillMaxHeight().weight(1f),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = event.user,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}


enum class EventType(val value: String) {
    ALL_EVENTS("All"),
    EVENT("Event"),
    TASK("Task"),
    PROJECT("Project"),
    LECTURE("Lecture")

}