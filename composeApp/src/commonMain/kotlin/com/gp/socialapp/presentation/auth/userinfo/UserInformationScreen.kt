package com.gp.socialapp.presentation.auth.userinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.auth.util.AuthError
import com.gp.socialapp.presentation.home.container.HomeContainer
import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.LocalDateTimeUtil.toDDMMYYYY
import com.gp.socialapp.util.LocalDateTimeUtil.toLocalDateTime
import com.gp.socialapp.util.LocalDateTimeUtil.toMillis
import com.gp.socialapp.util.LocalDateTimeUtil.toYYYYMMDD
import com.gp.socialapp.util.Result
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import com.mohamedrejeb.calf.picker.toImageBitmap
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.about
import socialmultiplatform.composeapp.generated.resources.cancel
import socialmultiplatform.composeapp.generated.resources.complete_profile
import socialmultiplatform.composeapp.generated.resources.complete_your_profile
import socialmultiplatform.composeapp.generated.resources.date_of_birth
import socialmultiplatform.composeapp.generated.resources.name
import socialmultiplatform.composeapp.generated.resources.phone_number
import socialmultiplatform.composeapp.generated.resources.select
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.gp.socialapp.presentation.auth.signup.Responsive

data class UserInformationScreen(
    val signedInUser: User,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel<UserInformationScreenModel>()
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
            val scope = rememberCoroutineScope()
            val context = LocalPlatformContext.current
            val pickerLauncher = rememberFilePickerLauncher(
                type = FilePickerFileType.Image,
                selectionMode = FilePickerSelectionMode.Single,
                onResult = { files ->
                    scope.launch {
                        files.firstOrNull()?.let { file ->
                            val image = file.readByteArray(context)
                            screenModel.onImageChange(image)
                        }
                    }
                }
            )

            UserInformationContent(
                paddingValues = PaddingValues(),
                state = state,
                onNameChange = { screenModel.onNameChange(it) },
                onProfileImageClicked = { pickerLauncher.launch() },
                onPhoneNumberChange = { screenModel.onPhoneNumberChange(it) },
                onBioChange = { screenModel.onBioChange(it) },
                onDateOfBirthChange = { screenModel.onBirthDateChange(it) },
                onContinueClicked = { screenModel.onCompleteAccount() },
                responsive = Responsive(isMobile = isMobile, isTablet = isTablet, isDesktop = isDesktop),
                deviceWidth = deviceWidth,
                deviceHeight = deviceHeight
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserInformationContent(
        paddingValues: PaddingValues,
        state: UserInformationUiState,
        onNameChange: (String) -> Unit = {},
        onProfileImageClicked: () -> Unit = {},
        onPhoneNumberChange: (String) -> Unit = {},
        onDateOfBirthChange: (LocalDateTime) -> Unit = {},
        onBioChange: (String) -> Unit = {},
        onContinueClicked: () -> Unit = {},
        responsive: Responsive,
        deviceWidth: Dp,
        deviceHeight: Dp
    ) {
        var isDateDialogOpen by remember { mutableStateOf(false) }
        var pickedDate by remember { mutableStateOf(LocalDateTime.now()) }
        val formattedDate by remember {
            derivedStateOf {
                pickedDate.toYYYYMMDD()
            }
        }
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        val paddingSize = with(LocalDensity.current) { deviceWidth.toPx() / 20 }.dp
        val textSize = with(LocalDensity.current) { deviceWidth.toPx() / 40 }.sp
        val componentHeight = with(LocalDensity.current) { deviceHeight.toPx() / 15 }.dp

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            modifier = Modifier.fillMaxSize(),
        ) {
            if (state.error is AuthError.ServerError) {
                scope.launch {
                    snackbarHostState.showSnackbar((state.error as AuthError.ServerError).message)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .widthIn(max = when {
                        responsive.isMobile -> deviceWidth * 0.9f
                        responsive.isTablet -> deviceWidth * 0.7f
                        else -> deviceWidth * 0.5f
                    })
                    .padding(paddingSize),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.complete_your_profile),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally),
                    fontSize = textSize,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val imageModifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                    if (state.pfpImageByteArray.isEmpty()) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            modifier = imageModifier,
                            tint = MaterialTheme.colorScheme.outline
                        )
                    } else {
                        val bitmap = state.pfpImageByteArray.toImageBitmap()
                        Image(
                            bitmap = bitmap,
                            contentDescription = null,
                            modifier = imageModifier,
                            contentScale = ContentScale.Crop
                        )
                    }
                    IconButton(
                        onClick = { onProfileImageClicked() },
                        modifier = Modifier
                            .offset(x = 38.dp, y = 38.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Create,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
                OutlinedTextField(
                    value = state.name,
                    onValueChange = onNameChange,
                    label = { Text(text = stringResource(Res.string.name)) },
                    isError = state.error is AuthError.FirstNameError,
                    supportingText = {
                        if (state.error is AuthError.FirstNameError) {
                            Text(
                                text = (state.error as AuthError.FirstNameError).message,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
                OutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = onPhoneNumberChange,
                    label = { Text(text = stringResource(Res.string.phone_number)) },
                    isError = state.error is AuthError.PhoneNumberError,
                    supportingText = {
                        if (state.error is AuthError.PhoneNumberError) {
                            Text(
                                text = (state.error as AuthError.PhoneNumberError).message,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.PhoneAndroid,
                            contentDescription = null,
                        )
                    }
                )
                Box {
                    OutlinedTextField(
                        value = formattedDate,
                        onValueChange = {},
                        label = { Text(text = stringResource(Res.string.date_of_birth)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .clickable {
                                isDateDialogOpen = true
                            },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.CalendarMonth,
                                contentDescription = null,
                            )
                        },
                        isError = state.error is AuthError.BirthDateError,
                        supportingText = {
                            if (state.error is AuthError.BirthDateError) {
                                Text(
                                    text = (state.error as AuthError.BirthDateError).message,
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 12.sp
                                )
                            }
                        },
                        maxLines = 1,
                        readOnly = true,
                        enabled = false
                    )
                }
                OutlinedTextField(
                    value = state.bio,
                    onValueChange = onBioChange,
                    label = { Text(text = stringResource(Res.string.about)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null,
                        )
                    },
                    maxLines = 3,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                )
                Button(
                    onClick = onContinueClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(componentHeight),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.complete_profile),
                        fontSize = 18.sp,
                    )
                }
                if (isDateDialogOpen) {
                    val datePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = LocalDateTime.now().toMillis()
                    )
                    val confirmEnabled = remember {
                        derivedStateOf { datePickerState.selectedDateMillis != null }
                    }
                    DatePickerDialog(
                        onDismissRequest = {
                            isDateDialogOpen = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    isDateDialogOpen = false
                                    val date = datePickerState.selectedDateMillis?.toLocalDateTime()
                                        ?: LocalDateTime.now()
                                    pickedDate = date
                                    onDateOfBirthChange(date)
                                },
                                enabled = confirmEnabled.value
                            ) {
                                Text(stringResource(Res.string.select))
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    isDateDialogOpen = false
                                }
                            ) {
                                Text(stringResource(Res.string.cancel))
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
            }
        }
    }
}
