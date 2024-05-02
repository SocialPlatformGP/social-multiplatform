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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.change_password
import socialmultiplatform.composeapp.generated.resources.hide_password
import socialmultiplatform.composeapp.generated.resources.new_password
import socialmultiplatform.composeapp.generated.resources.old_password
import socialmultiplatform.composeapp.generated.resources.password
import socialmultiplatform.composeapp.generated.resources.retype_password
import socialmultiplatform.composeapp.generated.resources.show_password

@Composable
fun PasswordSettingItem(
    modifier: Modifier = Modifier,
    onSave: (String, String) -> Unit,
    onCheckOldPassword: (String) -> Boolean,
    onCheckNewPassword: (String) -> Boolean,
) {
    var isDialogShown by remember {
        mutableStateOf(false)
    }
    if (isDialogShown) {
        Dialog(onDismissRequest = {
            isDialogShown = false
        }) {
            PasswordEditDialog(onSave, onCheckOldPassword, onCheckNewPassword) {
                isDialogShown = false
            }
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color.Transparent,
        onClick = {
            isDialogShown = true
        },
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = stringResource(Res.string.password),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "********",
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
private fun PasswordEditDialog(
    onSave: (String, String) -> Unit,
    onCheckOldPassword: (String) -> Boolean,
    onCheckNewPassword: (String) -> Boolean,
    onDismiss: () -> Unit
) {
    var oldPassword by remember { mutableStateOf("") }
    var oldPasswordVisible by remember { mutableStateOf(false) }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var retypePassword by remember { mutableStateOf("") }
    var isOldPasswordValid by remember { mutableStateOf(true) }
    var isNewPasswordValid by remember { mutableStateOf(true) }
    var isRetypePasswordValid by remember { mutableStateOf(true) }

    Surface(
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(stringResource(Res.string.change_password), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = oldPassword,
                onValueChange = {
                    isOldPasswordValid = onCheckOldPassword(it)
                    oldPassword = it
                },
                label = { Text(text = stringResource(Res.string.old_password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                    )
                },
                isError = !isOldPasswordValid,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (oldPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (oldPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description =
                        stringResource(if (oldPasswordVisible) Res.string.hide_password else Res.string.show_password)
                    IconButton(onClick = { oldPasswordVisible = !oldPasswordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            OutlinedTextField(
                value = newPassword,
                onValueChange = {
                    isNewPasswordValid = onCheckNewPassword(it)
                    newPassword = it
                },
                label = { Text(text = stringResource(Res.string.new_password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                    )
                },
                isError = !isNewPasswordValid,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (newPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description =
                        stringResource(if (newPasswordVisible) Res.string.hide_password else Res.string.show_password)
                    IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            OutlinedTextField(
                value = retypePassword,
                onValueChange = {
                    retypePassword = it
                    isRetypePasswordValid = newPassword == retypePassword
                },
                label = { Text(text = stringResource(Res.string.retype_password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                    )
                },
                isError = !isRetypePasswordValid,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (newPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description =
                        stringResource(if (newPasswordVisible) Res.string.hide_password else Res.string.show_password)
                    IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    onSave(oldPassword, newPassword)
                    onDismiss()
                }, enabled = isOldPasswordValid && isNewPasswordValid && isRetypePasswordValid) {
                    Text("Save")
                }
            }
        }
    }
}