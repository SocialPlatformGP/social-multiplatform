package com.gp.socialapp.presentation.auth.signup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpScreenModel(
    private val authRepo: AuthenticationRepository
) : ScreenModel{
    val uiState = MutableStateFlow(SignUpUiState())
    fun onSignUp(){
        screenModelScope.launch {
            with(uiState.value){
                val state = authRepo.isUserExists(email, password)
                state.collect{
                    uiState.value = uiState.value.copy(userExists = it)
                }
            }
        }
    }
    fun onEmailChange(email: String){
        uiState.update { it.copy(email = email) }
    }
    fun onPasswordChange(password: String){
        uiState.update { it.copy(password = password) }
    }
    fun rePasswordChange(rePassword: String){
        uiState.update { it.copy(rePassword = rePassword) }
    }
}