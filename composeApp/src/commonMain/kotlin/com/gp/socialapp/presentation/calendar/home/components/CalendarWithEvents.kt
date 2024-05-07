package com.gp.socialapp.presentation.calendar.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.calendar.model.CalendarEvent
import korlibs.time.DateTime
import korlibs.time.months

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarWithEvents(
    modifier: Modifier = Modifier,
    events: List<CalendarEvent>,
) {
    val currentYearMonth = remember { mutableStateOf(DateTime.now().yearMonth) }
    // MutableState for selected date events
    val selectedDateEvents = remember { mutableStateOf<List<CalendarEvent>>(listOf()) }
    HorizontalPager(
        modifier = Modifier.fillMaxWidth().then(modifier),
        state = rememberPagerState(initialPage = Int.MAX_VALUE / 2, pageCount = {Int.MAX_VALUE}),
    ) { page ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            CalendarHeader(
                currentYearMonth = currentYearMonth,
                onNextMonth = {
                    currentYearMonth.value = currentYearMonth.value + 1.months
                },
                onPreviousMonth = {
                    currentYearMonth.value = currentYearMonth.value - 1.months
                }
            )
            CalendarGrid(
                events = events,
                currentYearMonth = currentYearMonth.value,
                selectedDateEvents = selectedDateEvents,
            )
            //TODO event list
            EventList(
                events = selectedDateEvents.value
            )
        }
    }
}
