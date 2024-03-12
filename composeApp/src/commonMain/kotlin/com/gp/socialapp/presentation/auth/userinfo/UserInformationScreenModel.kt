package com.gp.socialapp.presentation.auth.userinfo

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.LocalDateTimeUtil.toMillis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class UserInformationScreenModel(
//    private val userRepo: UserRepository,
    private val authRepo: AuthenticationRepository,
): ScreenModel {
    private val _uiState = MutableStateFlow(UserInformationUiState())
    val uiState = _uiState.asStateFlow()
    fun onCompleteAccount(email: String, password: String) {
        //TODO("Validate User Information")
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
                    _uiState.value = uiState.value.copy(createdState = state)
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