package com.gp.socialapp.presentation.calendar.createevent.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gp.socialapp.util.LocalDateTimeUtil.toLocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.cancel
import socialmultiplatform.composeapp.generated.resources.select
import java.util.Calendar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CreateEventTime(
    modifier: Modifier = Modifier,
    onTimeChanged: (Long) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).then(modifier)
    ) {
        val timePickerState = rememberTimePickerState(
            initialHour = 12,
            initialMinute = 30,
            is24Hour = true
        )
        var showTimePicker by remember { mutableStateOf(false) }
        OutlinedButton(
            onClick = {
                showTimePicker = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(top = 4.dp, start = 8.dp, end = 8.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = timePickerState.hour.toString() + ":" + timePickerState.minute.toString())
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))

        if (showTimePicker) {
            Dialog(
                onDismissRequest = { showTimePicker = false },
                properties = DialogProperties(usePlatformDefaultWidth = false),

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
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            text = "Select Time",
                            style = MaterialTheme.typography.labelMedium
                        )
                        TimePicker(
                            state = timePickerState
                        )
                        Row(
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            TextButton(
                                onClick = {
                                    showTimePicker = false
                                }
                            ) {
                                Text(stringResource(Res.string.cancel))
                            }
                            TextButton(
                                onClick = {
                                    showTimePicker = false
                                    val calendar = Calendar.getInstance()
                                    calendar.set(Calendar.HOUR, timePickerState.hour)
                                    calendar.set(Calendar.MINUTE, timePickerState.minute)
                                    val time =
                                        calendar.time.time.toLocalDateTime().toInstant(TimeZone.UTC)
                                            .toEpochMilliseconds()
                                    onTimeChanged(time)

                                }
                            ) {
                                Text(stringResource(Res.string.select))
                            }

                        }
                    }
                }

            }

        }
    }
}