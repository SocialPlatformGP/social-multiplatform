package com.gp.socialapp.presentation.auth.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.auth.passwordreset.PasswordResetScreen
import com.gp.socialapp.presentation.auth.signup.SignUpScreen
import com.gp.socialapp.util.AuthError
import com.gp.socialapp.util.AuthError.ServerError
import com.gp.socialapp.util.AuthError.EmailError
import com.gp.socialapp.util.AuthError.PasswordError
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res

object LoginScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<LoginScreenModel>()
        val state by screenModel.uiState.collectAsState()
        if(state.token != null){
            //todo navigate to main
        } else {
            LoginContent(
                onSignInWithGoogle = { /*todo*/},
                state = state,
                navigateToSignUp = { navigator.push(SignUpScreen) },
                navigateToForgotPassword = {navigator.push(PasswordResetScreen)},
                onEmailChange = {screenModel.updateEmail(it)  },
                onPasswordChange = { screenModel.updatePassword(it) },
                onSignIn = { screenModel.onSignIn() }
            )
        }
    }
    @Composable
    private fun LoginContent(
        onSignInWithGoogle: () -> Unit,
        onSignIn : () -> Unit,
        state: LoginUiState,
        navigateToSignUp: () -> Unit,
        navigateToForgotPassword: () -> Unit,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
    ) {
        var passwordVisible by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold (
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) },
            modifier = Modifier.fillMaxSize(),
        ) {
            if(state.error is ServerError){
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = (state.error as ServerError).message,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
            ) {
//            Image(painter = painterResource(resource = Res.drawable.login), contentDescription = null)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    fontSize = 42.sp,
                    text= stringResource(resource = Res.string.welcome_back),
                )
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { onEmailChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    label = { Text(text = stringResource(Res.string.email)) },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null,) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = state.error is EmailError,
                    supportingText = {
                        if(state.error is EmailError){
                            Text(
                                text = (state.error as EmailError).message,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                        }
                    }
                )
                OutlinedTextField(
                    value = state.password,
                    onValueChange = { onPasswordChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    label = { Text(text = stringResource(Res.string.password),) },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = stringResource(if (passwordVisible) Res.string.hide_password else Res.string.show_password)
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description,)
                        }
                    },
                    isError = state.error is PasswordError,
                    supportingText = {
                        if(state.error is PasswordError ){
                            Text(
                                text = (state.error as PasswordError).message,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                        }
                    }
                )
                TextButton(
                    onClick = navigateToForgotPassword,
                    enabled = false,
                    modifier = Modifier
                        .padding(start = 16.dp),
                ) {
                    Text(
                        text = stringResource(resource = Res.string.forgot_password),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                    )
                }
                Button(
                    onClick = onSignIn,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = stringResource(resource = Res.string.login_str),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Text(
                    text = stringResource(Res.string.or_login_with),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    fontSize = 16.sp,
                )
                OutlinedButton(
                    onClick = { onSignInWithGoogle() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                ) {
//            Icon(
//                painter = painterResource(resource = Res.drawable.google),
//                contentDescription = null,
//                tint = androidx.compose.ui.graphics.Color.Unspecified,
//                modifier = Modifier.size(24.dp)
//            )
                    Text(
                        text = stringResource(resource = Res.string.sign_in_with_google),
                        fontSize = 18.sp,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = stringResource(resource = Res.string.dont_have_an_account), fontSize = 16.sp)
                    TextButton(onClick = navigateToSignUp) {
                        Text(
                            text = stringResource(resource = Res.string.sign_up),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                        )
                    }
                }

            }
        }
    }

}
