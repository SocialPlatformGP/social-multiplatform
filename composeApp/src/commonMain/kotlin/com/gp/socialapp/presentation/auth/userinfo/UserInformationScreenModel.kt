package com.gp.socialapp.presentation.auth.userinfo

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.UserRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class UserInformationScreenModel(
    private val userRepo: UserRepository
): ScreenModel {
    val uiState = MutableStateFlow(UserInformationUiState())
    fun onCompleteAccount(email: String, password: String) {
        screenModelScope.launch {
            with(uiState.value) {
                val networkFlow =
                    userRepo.createUser(
                        User(
                            firstName, lastName, password, "", email,
                            phoneNumber, birthDate, bio,
                        ), pfpLocalURI)
                networkFlow.collect { state ->
                    uiState.value = uiState.value.copy(createdState = state)
                }
            }
        }
    }
    fun onFirstNameChange(firstName: String) {
        uiState.update { it.copy(firstName = firstName) }
    }
    fun onLastNameChange(lastName: String) {
        uiState.update { it.copy(lastName = lastName) }
    }
    fun onPhoneNumberChange(phoneNumber: String) {
        uiState.update { it.copy(phoneNumber = phoneNumber) }
    }
    fun onBirthDateChange(birthDate: LocalDateTime) {
        uiState.update { it.copy(birthDate = birthDate) }
    }
    fun onBioChange(bio: String) {
        uiState.update { it.copy(bio = bio) }
    }
}