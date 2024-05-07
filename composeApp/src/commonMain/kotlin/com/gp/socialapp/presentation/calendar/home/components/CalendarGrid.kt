package com.gp.socialapp.presentation.calendar.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.calendar.model.CalendarEvent
import korlibs.time.DateTime
import korlibs.time.YearMonth
import kotlinx.datetime.number

@Composable
fun CalendarGrid(
    modifier: Modifier = Modifier,
    events: List<CalendarEvent>,
    currentYearMonth: YearMonth,
    selectedDateEvents: MutableState<List<CalendarEvent>>,
) {
    val firstDayOfMonth = DateTime(currentYearMonth.yearInt, currentYearMonth.month1, 1)
    val startingDayOfWeek = firstDayOfMonth.dayOfWeek.index0
    val days = (1..currentYearMonth.days).toList()
    val paddingDaysBefore = List(startingDayOfWeek) { -1 }
    val paddingDaysAfter = List((7 - (startingDayOfWeek + currentYearMonth.days) % 7) % 7) { -1 }
    val allDays = paddingDaysBefore + days + paddingDaysAfter
    Column{
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ){
            val weekDays = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            weekDays.forEach {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
        HorizontalDivider()
        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            items(allDays.size) { index ->
                val day = allDays[index]
                val currentDay = if (day <= 0) "" else day.toString()
                val eventsForDay = events.filter {
                    it.dateTime.dayOfMonth == day && it.dateTime.month.number == currentYearMonth.month1
                }
                val eventDots = buildString {
                    repeat(eventsForDay.size) {
                        append("• ")
                    }
                }
                IconButton(
                    onClick = {
                        // On day click, update selected date events
                        selectedDateEvents.value = eventsForDay
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(2.dp),
                    ) {
                        Text(
                            text = currentDay,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        Text(
                            text = eventDots,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }

            }
        }
    }
}