package com.gp.socialapp.presentation.auth.signup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpScreenModel(
    private val authRepo: AuthenticationRepository
) : ScreenModel{
    val uiState = MutableStateFlow(SignUpUiState())
    fun onSignUp(){
        //TODO: Validate Email & Password
        screenModelScope.launch {
            Napier.d ("onSignUp: ${uiState.value}")
            println("onSignUp: ${uiState.value}")
            with(uiState.value){
                authRepo.isEmailAvailable(email).collect{
                    when (it){
                        is Result.SuccessWithData -> {
                            uiState.value = uiState.value.copy(userExists = it)
                        }
                        is Result.Error -> {
                            Napier.d("onSignUp: Error ${it.message}")

                        }
                        else -> {
                            Napier.d("onSignUp: else")
                        }
                    }

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