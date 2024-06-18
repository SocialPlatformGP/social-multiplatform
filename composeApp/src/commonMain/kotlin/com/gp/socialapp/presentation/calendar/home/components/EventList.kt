package com.gp.socialapp.presentation.calendar.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.calendar.model.CalendarEvent

@Composable
fun EventList(
    modifier: Modifier = Modifier,
    events: List<CalendarEvent>
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        println("EventList: ${events}")
        items(events) {
            EventItem(
                event = it
            )
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
private fun EventItem(
    modifier: Modifier = Modifier,
    event: CalendarEvent
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { }
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Box(
            modifier = Modifier
                .widthIn(min = 80.dp, max = 120.dp)
                .padding(8.dp)
                .background(
                    MaterialTheme.colorScheme.onPrimaryContainer,
                    RoundedCornerShape(4.dp)
                )

        ) {
            Text(
                text = event.type,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(8.dp).align(Alignment.Center)
            )
        }
        Column(
            modifier = Modifier.padding(8.dp).weight(0.85f)
        ) {
            Text(
                text = event.title,
                color = MaterialTheme.colorScheme.onPrimaryContainer,

                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = event.description,
                color = MaterialTheme.colorScheme.secondary,

                style = MaterialTheme.typography.bodySmall
            )
        }

    }

}

enum class EventType(val value: String) {
    EVENT("Event"),
    TASK("Task"),
}