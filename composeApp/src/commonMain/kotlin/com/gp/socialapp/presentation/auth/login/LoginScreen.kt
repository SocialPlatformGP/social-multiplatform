package com.gp.socialapp.presentation.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.auth.login.components.MyOAuthProvider
import com.gp.socialapp.presentation.auth.login.components.OAuthProviderItem
import com.gp.socialapp.presentation.auth.login.components.imagevectors.OAuthProviderIcons
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Discord
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Facebook
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Github
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Google
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Linkedin
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Microsoft
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Slack
import com.gp.socialapp.presentation.auth.login.components.imagevectors.oauthprovidericons.Twitter
import com.gp.socialapp.presentation.auth.passwordreset.PasswordResetScreen
import com.gp.socialapp.presentation.auth.signup.Responsive
import com.gp.socialapp.presentation.auth.signup.SignUpScreen
import com.gp.socialapp.presentation.auth.util.AuthError.EmailError
import com.gp.socialapp.presentation.auth.util.AuthError.PasswordError
import com.gp.socialapp.presentation.auth.util.AuthError.ServerError
import com.gp.socialapp.presentation.home.container.HomeContainer
import com.gp.socialapp.presentation.settings.components.AppThemeOptions
import com.gp.socialapp.theme.LocalThemeIsDark
import io.github.jan.supabase.gotrue.providers.Azure
import io.github.jan.supabase.gotrue.providers.Discord
import io.github.jan.supabase.gotrue.providers.Github
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import io.github.jan.supabase.gotrue.providers.Slack
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.dont_have_an_account
import socialmultiplatform.composeapp.generated.resources.email
import socialmultiplatform.composeapp.generated.resources.forgot_password
import socialmultiplatform.composeapp.generated.resources.hide_password
import socialmultiplatform.composeapp.generated.resources.login_str
import socialmultiplatform.composeapp.generated.resources.or_login_with
import socialmultiplatform.composeapp.generated.resources.password
import socialmultiplatform.composeapp.generated.resources.show_password
import socialmultiplatform.composeapp.generated.resources.sign_up

object LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<LoginScreenModel>()
        val state by screenModel.uiState.collectAsState()
        var isDark by LocalThemeIsDark.current
        val isSystemInDarkTheme = isSystemInDarkTheme()
        isDark = when (state.theme) {
            AppThemeOptions.LIGHT.value -> false
            AppThemeOptions.DARK.value -> true
            else -> isSystemInDarkTheme
        }
        LifecycleEffect(
            onStarted = { screenModel.init() },
            onDisposed = { screenModel.dispose() },
        )

        if (state.signedInUser != null) {
            navigator.replaceAll(HomeContainer())
        } else {
            var deviceWidth by remember { mutableStateOf(0.dp) }
            var deviceHeight by remember { mutableStateOf(0.dp) }
            val isMobile by remember { derivedStateOf { deviceWidth < 600.dp } }
            val isTablet by remember { derivedStateOf { deviceWidth in 600.dp..1100.dp } }
            val isDesktop by remember { derivedStateOf { deviceWidth > 1100.dp } }

            val responsive = Responsive(isMobile = isMobile, isTablet = isTablet, isDesktop = isDesktop)

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LaunchedEffect(this.maxWidth, this.maxHeight) {
                    deviceWidth = this@BoxWithConstraints.maxWidth
                    deviceHeight = this@BoxWithConstraints.maxHeight
                }

                LoginContent(
                    oAuthProviders = listOf(
                        MyOAuthProvider("Google", OAuthProviderIcons.Google, Google),
                        MyOAuthProvider("Microsoft", OAuthProviderIcons.Microsoft, Azure),
                        MyOAuthProvider("GitHub", OAuthProviderIcons.Github, Github),
                        MyOAuthProvider("Discord", OAuthProviderIcons.Discord, Discord),
                        MyOAuthProvider("Slack", OAuthProviderIcons.Slack, Slack),
                    ),
                    onSignInWithOAuth = { provider -> screenModel.signInWithOAuth(provider) },
                    state = state,
                    navigateToSignUp = { navigator.push(SignUpScreen) },
                    navigateToForgotPassword = { navigator.push(PasswordResetScreen) },
                    onEmailChange = { screenModel.updateEmail(it) },
                    onPasswordChange = { screenModel.updatePassword(it) },
                    onSignIn = { screenModel.onSignIn() },
                    responsive = responsive,
                    deviceWidth = deviceWidth,
                    deviceHeight = deviceHeight
                )
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun LoginContent(
        oAuthProviders: List<MyOAuthProvider>,
        onSignInWithOAuth: (OAuthProvider) -> Unit,
        onSignIn: () -> Unit,
        state: LoginUiState,
        navigateToSignUp: () -> Unit,
        navigateToForgotPassword: () -> Unit,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        responsive: Responsive,
        deviceWidth: Dp,
        deviceHeight: Dp
    ) {
        var passwordVisible by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        var password by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        val textSize = with(LocalDensity.current) { deviceWidth.toPx() / 60 }.sp
        val paddingSize = with(LocalDensity.current) { deviceWidth.toPx() / 50 }.dp
        val componentHeight = with(LocalDensity.current) { deviceHeight.toPx() / 30 }.dp

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            modifier = Modifier.fillMaxSize(),
        ) {
            if (state.error is ServerError) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = (state.error as ServerError).message,
                    )
                }
            }
            BoxWithConstraints(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
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
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp))
                        .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                        .padding(paddingSize),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(resource = Res.string.login_str),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        fontSize = textSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(paddingSize))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            onEmailChange(it)
                        },
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

                    Spacer(modifier = Modifier.height(paddingSize/2))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            onPasswordChange(it)
                        },
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

                    Spacer(modifier = Modifier.height(paddingSize/2))

                    TextButton(
                        onClick = navigateToForgotPassword,
                        modifier = Modifier.padding(start = 16.dp),
                    ) {
                        Text(
                            text = stringResource(resource = Res.string.forgot_password),
                            fontSize = textSize /2 ,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(paddingSize/2))

                    Button(
                        onClick = onSignIn,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(componentHeight *2),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(resource = Res.string.login_str),
                            fontSize = textSize,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Spacer(modifier = Modifier.height(paddingSize/2))

                    Text(
                        text = stringResource(Res.string.or_login_with),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        fontSize = textSize/2,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                    ) {
                        val iconSize = with(LocalDensity.current) { (deviceWidth.toPx() / 12).dp/2 }
                        oAuthProviders.forEach { provider ->
                            OAuthProviderItem(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(iconSize),
                                provider = provider,
                                onClick = onSignInWithOAuth,
                                isEnabled = true,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(paddingSize/2))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.dont_have_an_account),
                            fontSize = textSize/2,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        TextButton(
                            onClick = navigateToSignUp,
                            modifier = Modifier.padding(start = 4.dp),
                        ) {
                            Text(
                                text = stringResource(resource = Res.string.sign_up),
                                fontSize = textSize,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(textDecoration = TextDecoration.Underline),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
