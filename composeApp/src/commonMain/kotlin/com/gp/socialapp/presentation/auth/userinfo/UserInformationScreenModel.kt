package com.gp.socialapp.presentation.auth.userinfo

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.auth.util.AuthError
import com.gp.socialapp.presentation.auth.util.Validator
import com.gp.socialapp.util.LocalDateTimeUtil.toMillis
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.getString
import socialmultiplatform.composeapp.generated.resources.Res

class UserInformationScreenModel(
    private val authRepo: AuthenticationRepository,
) : ScreenModel {
    private val _uiState = MutableStateFlow(UserInformationUiState())
    val uiState = _uiState.asStateFlow()
    fun onCompleteAccount(email: String, password: String) {
        with(_uiState.value) {
            if (!Validator.NameValidator.validateAll(firstName)) {
                screenModelScope.launch {
                    val error = AuthError.FirstNameError(getString(Res.string.invalid_first_name))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = AuthError.NoError)
            }
            if (!Validator.NameValidator.validateAll(lastName)) {
                screenModelScope.launch {
                    val error = AuthError.LastNameError(getString(Res.string.invalid_last_name))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = AuthError.NoError)
            }
            if (!Validator.PhoneNumberValidator.validateAll(phoneNumber)) {
                screenModelScope.launch {
                    val error =
                        AuthError.PhoneNumberError(getString(Res.string.invalid_phone_number))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = AuthError.NoError)
            }
            if (!Validator.BirthDateValidator.validateAll(birthDate)) {
                screenModelScope.launch {
                    val error =
                        AuthError.BirthDateError(getString(Res.string.user_must_be_at_least_18_years_old))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = AuthError.NoError)
            }
        }
        screenModelScope.launch {
            with(uiState.value) {
                authRepo.signUpUser(
                    User(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        password = password,
                        phoneNumber = phoneNumber,
                        birthdate = birthDate.toMillis(),
                        bio = bio,
                    )
                ).collect { state ->
                    when (state) {
                        is Result.SuccessWithData -> {
                            _uiState.value = uiState.value.copy(createdState = state)
                            authRepo.setLocalUserToken(state.data.token)
                        }

                        is Result.Error -> {
                            val error = AuthError.ServerError(state.message)
                            _uiState.value = uiState.value.copy(error = error)
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    fun onFirstNameChange(firstName: String) {
        _uiState.update { it.copy(firstName = firstName) }
    }

    fun onLastNameChange(lastName: String) {
        _uiState.update { it.copy(lastName = lastName) }
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun onBirthDateChange(birthDate: LocalDateTime) {
        _uiState.update { it.copy(birthDate = birthDate) }
    }

    fun onBioChange(bio: String) {
        _uiState.update { it.copy(bio = bio) }
    }
}