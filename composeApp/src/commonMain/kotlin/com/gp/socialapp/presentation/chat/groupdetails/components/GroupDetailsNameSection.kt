package com.gp.socialapp.presentation.chat.groupdetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
    Row(
        modifier = modifier.padding(vertical = 8.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (isModifying) {
            TextField(
                value = newName,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.surfaceTint,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceTint,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                ),
                trailingIcon = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "${newName.length} /24",
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        IconButton(
                            onClick = {
                                isModifying = !isModifying
                                onChangeName(newName)
                            },
                            enabled = !isError,
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Check, contentDescription = null
                            )
                        }
                    }

                },
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
                    isError = it.isBlank() || it.length > 24
                    newName = it
                },
                placeholder = {
                    Text(text = "Group Name")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    if (!isError) {
                        isModifying = !isModifying
                        onChangeName(newName)
                    }
                }),
                modifier = Modifier.fillMaxWidth()
                    .focusRequester(focusRequester),
            )
            LaunchedEffect(true) {
                focusRequester.requestFocus()
            }
        } else {
            Text(
                text = newName,
                style = MaterialTheme.typography.headlineLarge,
            )
        }
        if (isModifiable && !isModifying) {
            IconButton(
                onClick = {
                    isModifying = !isModifying
                }, modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Create, contentDescription = null
                )
            }
        }
    }
}