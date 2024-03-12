package com.gp.socialapp.presentation.auth.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.auth.util.Validator
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.util.AuthError.ServerError
import com.gp.socialapp.util.AuthError.PasswordError
import com.gp.socialapp.util.AuthError.NoError
import com.gp.socialapp.util.AuthError.EmailError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import socialmultiplatform.composeapp.generated.resources.Res

class LoginScreenModel(
    private val authRepo: AuthenticationRepository,

): ScreenModel {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
    fun onSignIn(){
        with(_uiState.value){
            if(!Validator.EmailValidator.validateAll(email)){
                screenModelScope.launch {
                    val error = EmailError(getString(Res.string.invalid_email))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = NoError)
            }
            if (password.length < 6 || !Validator.PasswordValidator.validateAll(password)){
                screenModelScope.launch {
                    val error = PasswordError(getString(Res.string.invalid_password))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else{
                _uiState.value = _uiState.value.copy(error = NoError)
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
                                error = NoError
                            )
                            //todo navigate to main and store token
                        }
                        is Result.Error -> {
                            _uiState.value = _uiState.value.copy(
                                token = "",
                                error = ServerError(it.message)
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