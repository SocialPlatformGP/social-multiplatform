package com.gp.socialapp.presentation.auth.passwordreset

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PasswordResetScreenModel(
    private val authRepo: AuthenticationRepository
) : ScreenModel{
    val uiState = MutableStateFlow(PasswordResetUiState())
    fun onSendResetEmail(){
        screenModelScope.launch {
            val state = authRepo.sendPasswordResetEmail(uiState.value.email)
            state.collect{
                uiState.value = uiState.value.copy(sentState = it)
            }
        }

    }
    fun onEmailChange(email: String){
        uiState.update { it.copy(email = email) }
    }
}