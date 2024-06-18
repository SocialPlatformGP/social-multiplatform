package com.gp.socialapp.presentation.calendar.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import korlibs.time.YearMonth

@Composable
fun CalendarHeader(
    modifier: Modifier = Modifier,
    currentYearMonth: MutableState<YearMonth>,
    onPreviousMonth:() -> Unit,
    onNextMonth:() -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().then(modifier)
    ) {
        IconButton(onClick = {
            onPreviousMonth()
        },
                    modifier = Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
            shape = CircleShape
        )
            ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous Month")
        }
        Text(
            text = "${currentYearMonth.value.month.name} ${currentYearMonth.value.year.year}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        IconButton(onClick = {
            onNextMonth()
        },
                    modifier = Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
            shape = CircleShape
        )) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Month")
        }
    }
}