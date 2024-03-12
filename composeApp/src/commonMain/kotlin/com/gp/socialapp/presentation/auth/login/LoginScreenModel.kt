package com.gp.socialapp.presentation.auth.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.presentation.auth.util.Validator
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.presentation.auth.util.AuthError.ServerError
import com.gp.socialapp.presentation.auth.util.AuthError.PasswordError
import com.gp.socialapp.presentation.auth.util.AuthError.NoError
import com.gp.socialapp.presentation.auth.util.AuthError.EmailError
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
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
    init {
        val token = authRepo.getLocalUserToken()
        Napier.d("token: ${token?:"null"}")
        if(token != null){
            _uiState.value = _uiState.value.copy(token = token)
        }
    }
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
                            println("token: ${it.data}")
                            _uiState.value = _uiState.value.copy(
                                token = it.data,
                                error = NoError
                            )
                            authRepo.setLocalUserToken(it.data)
                        }
                        is Result.Error -> {
                            _uiState.value = _uiState.value.copy(
                                token = null,
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

    fun onLogOut() {
        authRepo.clearStorage()
    }
}