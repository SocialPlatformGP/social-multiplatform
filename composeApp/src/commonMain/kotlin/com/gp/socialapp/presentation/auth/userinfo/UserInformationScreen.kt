package com.gp.socialapp.presentation.auth.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.LocalDateTimeUtil.toDDMMYYYY
import com.gp.socialapp.util.LocalDateTimeUtil.toLocalDateTime
import com.gp.socialapp.util.LocalDateTimeUtil.toMillis
import com.gp.socialapp.util.LocalDateTimeUtil.toYYYYMMDD
import com.gp.socialapp.util.Result
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res

data class UserInformationScreen(
    val email: String = "",
    val password: String = "",
): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<UserInformationScreenModel>()
        val state by screenModel.uiState.collectAsState()
        if(state.createdState is Result.SuccessWithData){
            val token = (state.createdState as Result.SuccessWithData).data
            println("Token: $token")
            //TODO("navigate to main with token)
        }
        Scaffold { paddingValues ->
            UserInformationContent(
                paddingValues = paddingValues,
                state = state,
                onFirstNameChange = { screenModel.onFirstNameChange(it) },
                onLastNameChange = { screenModel.onLastNameChange(it) },
                onProfileImageClicked = { /*todo*/ },
                onPhoneNumberChange = { screenModel.onPhoneNumberChange(it) },
                onBioChange = { screenModel.onBioChange(it) },
                onDateOfBirthChange = { screenModel.onBirthDateChange(it) },
                onContinueClicked = { screenModel.onCompleteAccount(email, password) }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserInformationContent(
        paddingValues: PaddingValues,
        state: UserInformationUiState,
        onFirstNameChange: (String) -> Unit = {},
        onLastNameChange: (String) -> Unit = {},
        onProfileImageClicked: () -> Unit = {},
        onPhoneNumberChange: (String) -> Unit = {},
        onDateOfBirthChange: (LocalDateTime) -> Unit = {},
        onBioChange: (String) -> Unit = {},
        onContinueClicked: () -> Unit = {},
    ) {
        var isDateDialogOpen by remember { mutableStateOf(false) }
        var pickedDate by remember { mutableStateOf(LocalDateTime.now()) }
        val formattedDate by remember {
            derivedStateOf {
                pickedDate.toYYYYMMDD()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .widthIn(max = 600.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.complete_your_profile),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally),
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .clickable {
                        onProfileImageClicked()
                    }
            ) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(
//                            if (state.pfpLocalURI == Uri.EMPTY) R.drawable.baseline_person_24 else state.pfpLocalURI
//                        )
//                        .crossfade(true)
//                        .build(),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .align(Alignment.Center),
//                    placeholder = painterResource(id = R.drawable.baseline_person_24)
//
//                )
//                Icon(
//                    imageVector = Icons.Filled.PhotoCamera,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .align(Alignment.BottomEnd)
//                        .padding(4.dp)
//                        .background(
//                            color = Color.White,
//                            shape = MaterialTheme.shapes.small
//                        ),
//                )
            }
            Row {
                OutlinedTextField(
                    value = state.firstName,
                    onValueChange = onFirstNameChange,
                    label = { Text(text = stringResource(Res.string.first_name)) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                )
                OutlinedTextField(
                    value = state.lastName,
                    onValueChange = onLastNameChange,
                    label = { Text(text = stringResource(Res.string.last_name)) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                )
            }
            OutlinedTextField(
                value = state.phoneNumber,
                onValueChange = onPhoneNumberChange,
                label = { Text(text = stringResource(Res.string.phone_number)) },
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
            Box(
                modifier = Modifier.clickable {
                    isDateDialogOpen = true
                }
            ) {
                OutlinedTextField(
                    value = state.birthDate.let { if (it == LocalDateTime.now()) "" else pickedDate.toDDMMYYYY()},
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
                    maxLines = 1,
                    readOnly = true,
                    enabled = true
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
                    .height(52.dp)
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
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onDismissRequest.
                        isDateDialogOpen = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                isDateDialogOpen = false
                                val date = datePickerState.selectedDateMillis?.toLocalDateTime() ?: LocalDateTime.now()
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
