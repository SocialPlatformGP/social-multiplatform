package com.gp.socialapp.presentation.chat.groupdetails.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun GroupDetailsNameSection(
    name: String,
    isModifiable: Boolean,
    onChangeName: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isModifying by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf(name) }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(name) {
        newName = name
    }
    Box(
        modifier = modifier.padding(vertical = 8.dp).fillMaxWidth()
    ) {
        if (isModifying) {
            OutlinedTextField(shape = RoundedCornerShape(32.dp),
                value = newName,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Group Name is Required",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                onValueChange = {
                    isError = it.isBlank()
                    newName = it
                },
                label = {
                    Text(text = "Group Name")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    isModifying = !isModifying
                    onChangeName(newName)
                }),
                modifier = Modifier.fillMaxWidth().align(Alignment.Center)
                    .focusRequester(focusRequester))
            LaunchedEffect(true) {
                focusRequester.requestFocus()
            }
        } else {
            Text(
                text = newName,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Center),
            )
        }
        if (isModifiable && !isModifying) {
            IconButton(
                onClick = {
                    isModifying = !isModifying
                }, modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Create, contentDescription = null
                )
            }
        }
    }
}