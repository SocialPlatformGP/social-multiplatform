package com.gp.socialapp.presentation.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res

object SignUpScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<SignUpScreenModel>()
        val state by screenModel.uiState.collectAsState()
        Scaffold { paddingValues ->
            SignUpContent(
                paddingValues = paddingValues,
                state = state,
                onNavigateToLoginScreen = {/*todo*/ },
                onCreateAccount = {screenModel.onSignUp()},
                onEmailChange = { screenModel.onEmailChange(it) },
                onPasswordChange = { screenModel.onPasswordChange(it) },
                onRePasswordChange = { screenModel.rePasswordChange(it) }

            )
        }
    }

    @Composable
    private fun SignUpContent(
        paddingValues: PaddingValues,
        state: SignUpUiState,
        onNavigateToLoginScreen: () -> Unit,
        onCreateAccount: () -> Unit,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onRePasswordChange: (String) -> Unit,
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .widthIn(max = 600.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            var passwordVisible by remember { mutableStateOf(false) }
            Text(
                text = stringResource(Res.string.create_account),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                fontSize = 36.sp,
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = { onEmailChange(it) },
                label = { Text(text = stringResource(Res.string.email)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = null,
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = { onPasswordChange(it) },
                label = { Text(text = stringResource(Res.string.password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image =
                        if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description =
                        stringResource(if (passwordVisible) Res.string.hide_password else Res.string.show_password)
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            OutlinedTextField(
                value = state.rePassword,
                onValueChange = { onRePasswordChange(it) },
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
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image =
                        if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description =
                        stringResource(if (passwordVisible) Res.string.hide_password else Res.string.show_password)
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Button(
                onClick = onCreateAccount,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .height(52.dp),
            ) {
                Text(
                    text = stringResource(Res.string.create_account),
                    fontSize = 18.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.already_have_an_account),
                    modifier = Modifier
                        .padding(end = 8.dp),
                    fontSize = 18.sp
                )
                TextButton(
                    onClick = onNavigateToLoginScreen,
                ) {
                    Text(
                        text = stringResource(Res.string.login_str),
                        fontSize = 18.sp,
                    )
                }

            }

        }
    }
}