package com.gp.socialapp.presentation.auth.passwordreset
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.presentation.auth.signup.Responsive
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

        var deviceWidth by remember { mutableStateOf(0.dp) }
        var deviceHeight by remember { mutableStateOf(0.dp) }
        val isMobile by remember { derivedStateOf { deviceWidth < 600.dp } }
        val isTablet by remember { derivedStateOf { deviceWidth in 600.dp..1100.dp } }
        val isDesktop by remember { derivedStateOf { deviceWidth > 1100.dp } }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LaunchedEffect(this.maxWidth, this.maxHeight) {
                deviceWidth = this@BoxWithConstraints.maxWidth
                deviceHeight = this@BoxWithConstraints.maxHeight
            }

            PasswordResetContent(
                state = state,
                onEmailChange = { screenModel.onEmailChange(it) },
                onSendResetEmail = { /* Implement send reset email functionality */ },
                responsive = Responsive(isMobile = isMobile, isTablet = isTablet, isDesktop = isDesktop),
                deviceWidth = deviceWidth,
                deviceHeight = deviceHeight
            )
        }
    }

    @Composable
    private fun PasswordResetContent(
        state: PasswordResetUiState,
        onEmailChange: (String) -> Unit,
        onSendResetEmail: () -> Unit,
        responsive: Responsive,
        deviceWidth: Dp,
        deviceHeight: Dp
    ) {
        val paddingSize = with(LocalDensity.current) { deviceWidth.toPx() / 20 }.dp
        val textSize = with(LocalDensity.current) { deviceWidth.toPx() / 40 }.sp
        val componentHeight = with(LocalDensity.current) { deviceHeight.toPx() / 15 }.dp

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentColor  = MaterialTheme.colorScheme.background,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(
                            max = when {
                                responsive.isMobile -> deviceWidth * 0.9f
                                responsive.isTablet -> deviceWidth * 0.7f
                                else -> deviceWidth * 0.5f
                            }
                        )
                        .padding(paddingSize)
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(Res.string.reset_your_password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.displayMedium.copy(color=MaterialTheme.colorScheme.primary),
                        maxLines = 1,
                        fontSize = textSize,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(paddingSize))

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
                            .padding(vertical = 8.dp),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    )

                    Button(
                        onClick = onSendResetEmail,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .height(componentHeight),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Icon(imageVector = Icons.Filled.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(Res.string.send_reset_email),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }
    }
}
