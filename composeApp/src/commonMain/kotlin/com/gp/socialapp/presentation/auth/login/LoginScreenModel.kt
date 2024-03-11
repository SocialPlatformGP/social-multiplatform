package com.gp.socialapp.presentation.auth.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.auth.util.Validator
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenModel(
    private val authRepo: AuthenticationRepository,

): ScreenModel {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
    fun onSignIn(){
        with(_uiState.value){
            if(!Validator.EmailValidator.validateAll(email)){
                _uiState.value = _uiState.value.copy(emailError = "Invalid Email")
                return
            } else {
                _uiState.value = _uiState.value.copy(emailError = "")
            }
            if (password.length < 6 || !Validator.PasswordValidator.validateAll(password)){
                _uiState.value = _uiState.value.copy(passwordError = "Invalid Password")
                return
            } else{
                _uiState.value = _uiState.value.copy(passwordError = "")
            }
        }
        screenModelScope.launch {
            with(_uiState.value){
                val state = authRepo.signInUser(email, password)
                state.collect{
                    when(it){
                        is Result.SuccessWithData -> {
                            _uiState.value = _uiState.value.copy(
                                token = it.data,
                                serverErrorMessage = ""
                            )
                        }
                        is Result.Error -> {
                            _uiState.value = _uiState.value.copy(
                                token = "",
                                serverErrorMessage = it.message
                            )
                        }
                        else->Unit
                    }
                }
            }
        }
    }
//    fun authenticateWithGoogle(account: GoogleSignInAccount) = authRepo.authenticateWithGoogle(account)

    fun updateEmail(email: String){
        _uiState.update { it.copy(email = email) }
    }
    fun updatePassword(password: String){
        _uiState.update { it.copy(password = password) }
    }
}