package com.gp.socialapp.presentation.calendar.createevent.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.LocalDateTimeUtil.toDDMMYYYY
import com.gp.socialapp.util.LocalDateTimeUtil.toLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.cancel
import socialmultiplatform.composeapp.generated.resources.date_of_birth
import socialmultiplatform.composeapp.generated.resources.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventDate(
    modifier: Modifier = Modifier,
    onDateChanged: (Long) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).then(modifier)
    ) {
        var isDateDialogOpen by remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState(
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val today = LocalDateTime.now().toInstant(TimeZone.UTC).toEpochMilliseconds()
                    return utcTimeMillis >= today
                }

                override fun isSelectableYear(year: Int): Boolean {
                    return year > 2022
                }
            }
        )
        var pickedDateMillis by remember { mutableStateOf(0L) }
        val formattedDate by remember {
            derivedStateOf {
                pickedDateMillis.toLocalDateTime().toDDMMYYYY()
            }
        }
        OutlinedTextField(
            value = formattedDate,
            onValueChange = {},
            label = { Text(text = "Event date") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .clickable {
                    isDateDialogOpen = true
                },

            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = null,
                )
            },
            maxLines = 1,
            readOnly = true,
            enabled = false
        )
        if (isDateDialogOpen) {
            val confirmEnabled = remember {
                derivedStateOf { datePickerState.selectedDateMillis != null }
            }
            DatePickerDialog(
                onDismissRequest = {
                    isDateDialogOpen = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            isDateDialogOpen = false
                            pickedDateMillis = datePickerState.selectedDateMillis ?: 0L
                            onDateChanged(pickedDateMillis)
                        },
                        enabled = confirmEnabled.value
                    ) {
                        Text(stringResource(Res.string.select))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            isDateDialogOpen = false
                        }
                    ) {
                        Text(stringResource(Res.string.cancel))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}