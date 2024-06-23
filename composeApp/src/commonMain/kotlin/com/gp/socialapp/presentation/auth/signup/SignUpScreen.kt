package com.gp.socialapp.presentation.auth.signup

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.auth.util.AuthError
import com.gp.socialapp.presentation.auth.util.AuthError.EmailError
import com.gp.socialapp.presentation.auth.util.AuthError.PasswordError
import com.gp.socialapp.presentation.auth.util.AuthError.RePasswordError
import com.gp.socialapp.presentation.home.container.HomeContainer
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.already_have_an_account
import socialmultiplatform.composeapp.generated.resources.create_account
import socialmultiplatform.composeapp.generated.resources.email
import socialmultiplatform.composeapp.generated.resources.hide_password
import socialmultiplatform.composeapp.generated.resources.image
import socialmultiplatform.composeapp.generated.resources.login_str
import socialmultiplatform.composeapp.generated.resources.password
import socialmultiplatform.composeapp.generated.resources.retype_password
import socialmultiplatform.composeapp.generated.resources.show_password

object SignUpScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<SignUpScreenModel>()
        val state by screenModel.uiState.collectAsState()

        if (state.signedUpUser != null) {
            navigator.replaceAll(HomeContainer())
        }

        var deviceWidth by remember { mutableStateOf(0.dp) }
        var deviceHeight by remember { mutableStateOf(0.dp) }
        val isMobile by remember { derivedStateOf { deviceWidth < 600.dp } }
        val isTablet by remember { derivedStateOf { deviceWidth in 600.dp..1100.dp } }
        val isDesktop by remember { derivedStateOf { deviceWidth > 1100.dp } }
        var showDrawer by remember { mutableStateOf(false) }
        val mainContentAlpha by animateFloatAsState(if (showDrawer) 0.6f else 1f)

        LaunchedEffect(key1 = isDesktop) {
            if (isDesktop) {
                showDrawer = false
            }
        }

        val responsive = Responsive(isMobile = isMobile, isTablet = isTablet, isDesktop = isDesktop)

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            LaunchedEffect(this.maxWidth, this.maxHeight) {
                deviceWidth = this@BoxWithConstraints.maxWidth
                deviceHeight = this@BoxWithConstraints.maxHeight
            }

            SignUpContent(
                state = state,
                onNavigateToLoginScreen = { navigator.pop() },
                onCreateAccount = { screenModel.onSignUp() },
                onEmailChange = { screenModel.onEmailChange(it) },
                onPasswordChange = { screenModel.onPasswordChange(it) },
                onRePasswordChange = { screenModel.rePasswordChange(it) },
                responsive = responsive,
                deviceWidth = deviceWidth,
                deviceHeight = deviceHeight
            )
        }
    }
    @Composable
    private fun SignUpContent(
        state: SignUpUiState,
        onNavigateToLoginScreen: () -> Unit,
        onCreateAccount: () -> Unit,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onRePasswordChange: (String) -> Unit,
        responsive: Responsive,
        deviceWidth: Dp,
        deviceHeight: Dp
    ) {
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        val textSize = with(LocalDensity.current) { deviceWidth.toPx() / 60 }.sp
        val paddingSize = with(LocalDensity.current) { deviceWidth.toPx() / 100 }.dp
        val componentHeight = with(LocalDensity.current) { deviceHeight.toPx() / 30 }.dp

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            modifier = Modifier.fillMaxSize(),
        ) {
            if (state.error is AuthError.ServerError) {
                scope.launch {
                    snackbarHostState.showSnackbar((state.error as AuthError.ServerError).message)
                }
            }

            BoxWithConstraints(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(resource = Res.drawable.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .padding(paddingSize)
                        .widthIn(
                            max = when {
                                responsive.isMobile -> deviceWidth * 0.8f
                                responsive.isTablet -> deviceWidth * 0.6f
                                else -> deviceWidth * 0.4f
                            }
                        )
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f), shape = RoundedCornerShape(16.dp))
                        .padding(paddingSize),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var passwordVisible by remember { mutableStateOf(false) }

                    Text(
                        text = stringResource(Res.string.create_account),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        fontSize = textSize,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(paddingSize))

                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { onEmailChange(it) },
                        label = { Text(text = stringResource(Res.string.email), fontSize = textSize) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = null,
                            )
                        },
                        isError = state.error is EmailError,
                        supportingText = {
                            if (state.error is EmailError) {
                                Text(text = (state.error as EmailError).message)
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    Spacer(modifier = Modifier.height(paddingSize))

                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { onPasswordChange(it) },
                        label = { Text(text = stringResource(Res.string.password), fontSize = textSize) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = null,
                            )
                        },
                        isError = state.error is PasswordError,
                        supportingText = {
                            if (state.error is PasswordError) {
                                Text(text = (state.error as PasswordError).message)
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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

                    Spacer(modifier = Modifier.height(paddingSize))

                    OutlinedTextField(
                        value = state.rePassword,
                        onValueChange = { onRePasswordChange(it) },
                        label = { Text(text = stringResource(Res.string.retype_password), fontSize = textSize) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = null,
                            )
                        },
                        isError = state.error is RePasswordError,
                        supportingText = {
                            if (state.error is RePasswordError) {
                                Text(text = (state.error as RePasswordError).message)
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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

                    Spacer(modifier = Modifier.height(paddingSize))

                    Button(
                        onClick = onCreateAccount,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(componentHeight * 2),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.create_account),
                            fontSize = textSize,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(paddingSize))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.already_have_an_account),
                            modifier = Modifier.padding(end = 8.dp),
                            fontSize = textSize
                        )
                        TextButton(
                            onClick = onNavigateToLoginScreen,
                        ) {
                            Text(
                                text = stringResource(Res.string.login_str),
                                fontSize = textSize,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }}
