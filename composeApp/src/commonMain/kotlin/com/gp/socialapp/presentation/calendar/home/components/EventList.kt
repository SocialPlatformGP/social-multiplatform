package com.gp.socialapp.presentation.calendar.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gp.socialapp.data.calendar.model.CalendarEvent

@Composable
fun EventList(
    modifier: Modifier = Modifier,
    events: List<CalendarEvent>
) {
    LazyColumn (
        modifier = modifier.fillMaxWidth()
    ) {
        items(events.size ) {
            val event = events[it]
            EventItem(
                event = event
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventItem(
    modifier: Modifier = Modifier,
    event: CalendarEvent
) {
    var isExpanded by remember { mutableStateOf(false) }
    ListItem(
        modifier = modifier
            .clickable {
                isExpanded = !isExpanded
            }.clip(RoundedCornerShape(2.dp)),
        headlineContent = {
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            if(isExpanded) {
                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = if(event.type == EventType.EVENT.value) Icons.Default.Event else Icons.Default.Task,
                contentDescription = "Person",
            )
        },
        trailingContent = {
            ExposedDropdownMenuDefaults.TrailingIcon(isExpanded)
        },
        tonalElevation = if(isExpanded) 4.dp else 0.dp,
        shadowElevation = if(isExpanded) 4.dp else 0.dp,
    )
}
enum class EventType(val value: String){
    EVENT("Event"),
    TASK("Task"),
}