package com.gp.socialapp.presentation.auth.passwordreset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.email
import socialmultiplatform.composeapp.generated.resources.reset_your_password
import socialmultiplatform.composeapp.generated.resources.send_reset_email

object PasswordResetScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<PasswordResetScreenModel>()
        val state by screenModel.uiState.collectAsState()
        Scaffold { paddingValues ->
            ForgetPasswordContent(
                modifier = Modifier.padding(paddingValues),
                state = state,
                onEmailChange = { screenModel.onEmailChange(it) },
                onSendResetEmail = { /*todo*/ }
            )
        }
    }

    @Composable
    private fun ForgetPasswordContent(
        modifier: Modifier = Modifier,
        state: PasswordResetUiState = PasswordResetUiState(),
        onEmailChange: (String) -> Unit = {},
        onSendResetEmail: () -> Unit = {}
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .widthIn(max = 600.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(Res.string.reset_your_password),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(
                        Alignment.CenterHorizontally
                    ),
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = { onEmailChange(it) },
                label = {
                    Text(
                        text = stringResource(Res.string.email),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = null,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp),
                onClick = onSendResetEmail,
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(Res.string.send_reset_email),
                    fontSize = 16.sp
                )
            }
        }
    }
}