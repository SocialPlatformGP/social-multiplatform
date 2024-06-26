package com.gp.socialapp.presentation.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberSettingItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    state: String,
    onSave: (String) -> Unit,
    inputFilter: (String) -> String, // input filter for the preference
    onCheck: (String) -> Boolean
) {

    var isDialogShown by remember {
        mutableStateOf(false)
    }

    if (isDialogShown) {
        Dialog(onDismissRequest = { isDialogShown = isDialogShown.not() }) {
            TextEditNumberDialog(title, state, inputFilter, onSave, onCheck) {
                isDialogShown = isDialogShown.not()
            }
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color.Transparent,
        onClick = {
            isDialogShown = isDialogShown.not()
        },
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                    )
                }
            }
            HorizontalDivider()
        }
    }
}

@Composable
private fun TextEditNumberDialog(
    title: String,
    storedValue: String,
    inputFilter: (String) -> String, // filters out not needed letters
    onSave: (String) -> Unit,
    onCheck: (String) -> Boolean,
    onDismiss: () -> Unit
) {

    var currentInput by remember {
        mutableStateOf(storedValue)
    }

    var isValid by remember {
        mutableStateOf(onCheck(storedValue))
    }
    Surface{
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(title)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = currentInput,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    val filteredText = inputFilter(it)
                    isValid = onCheck(filteredText)
                    currentInput = filteredText
                })
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    onSave(currentInput)
                    onDismiss()
                }, enabled = isValid) {
                    Text("Save")
                }
            }
        }
    }
}