package com.gp.socialapp.presentation.assignment.createassignment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gp.socialapp.presentation.settings.components.SwitchSettingItem
import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.LocalDateTimeUtil.toDDMMYYYY
import com.gp.socialapp.util.LocalDateTimeUtil.toHHMMTimestamp
import com.gp.socialapp.util.LocalDateTimeUtil.toLocalDateTime
import com.gp.socialapp.util.LocalDateTimeUtil.toYYYYMMDD
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
fun CreateAssignmentDueDate(
    modifier: Modifier = Modifier,
    onDueDateChanged: (Long) -> Unit,
) {
    Column (
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).then(modifier)
    ){
        var hasDueDate by remember { mutableStateOf(false) }
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
            })
        var pickedDateMillis by remember { mutableStateOf(0L) }
        val timePickerState = rememberTimePickerState(initialHour = 23, is24Hour = false)
        var isTimeDialogOpen by remember { mutableStateOf(false) }
        val formattedDate by remember {
            derivedStateOf {
                pickedDateMillis.toLocalDateTime().toDDMMYYYY()
            }
        }
        val formattedTime by remember {
            derivedStateOf {
                pickedDateMillis.toHHMMTimestamp()
            }
        }
        SwitchSettingItem(
            name = "Has due date",
            icon = Icons.Default.CalendarMonth,
            isChecked = hasDueDate,
            onClick = { isChecked ->
                hasDueDate = isChecked
            }
        )
        if (hasDueDate) {
            OutlinedTextField(
                value = formattedDate,
                onValueChange = {},
                label = { Text(text = stringResource(Res.string.date_of_birth)) },
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
//            OutlinedTextField(
//                value = formattedTime,
//                onValueChange = {},
//                label = { Text(text = "Time") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 4.dp)
//                    .clickable {
//                        isTimeDialogOpen = true
//                    },
//
//                leadingIcon = {
//                    Icon(
//                        imageVector = Icons.Filled.Schedule,
//                        contentDescription = null,
//                    )
//                },
//                maxLines = 1,
//                readOnly = true,
//                enabled = false
//            )
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
                                onDueDateChanged(pickedDateMillis)
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
//            else if (isTimeDialogOpen) {
//                TimePickerDialog(
//                    onCancel = { isTimeDialogOpen = false },
//                    onConfirm = {
//                        isTimeDialogOpen = false
//                        val extraMillis =
//                            timePickerState.hour * 60 * 60 * 1000 + timePickerState.minute * 60 * 1000
//                        onDueDateChanged(pickedDateMillis + extraMillis)
//                    },
//                ) {
//                    TimePicker(state = timePickerState)
//                }
//            }
        }
    }
}

@Composable
private fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}