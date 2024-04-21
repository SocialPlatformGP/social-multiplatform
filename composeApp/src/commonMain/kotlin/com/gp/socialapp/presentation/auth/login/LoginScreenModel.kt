package com.gp.socialapp.presentation.auth.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.presentation.auth.util.AuthError.EmailError
import com.gp.socialapp.presentation.auth.util.AuthError.NoError
import com.gp.socialapp.presentation.auth.util.AuthError.PasswordError
import com.gp.socialapp.presentation.auth.util.AuthError.ServerError
import com.gp.socialapp.presentation.auth.util.Validator
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.invalid_email
import socialmultiplatform.composeapp.generated.resources.invalid_password

class LoginScreenModel(
    private val authRepo: AuthenticationRepository,

    ) : ScreenModel {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val userId = authRepo.getCurrentLocalUserId()
        if (userId.isNotBlank()) {
            _uiState.update { it.copy(userId = userId)}
            getSignedInUser(userId)
        }
    }

    private fun getSignedInUser(userId: String) {
        screenModelScope.launch (DispatcherIO){
            authRepo.getSignedInUser(userId).collect {
                when (it) {
                    is Result.SuccessWithData -> {
                        _uiState.value = _uiState.value.copy(signedInUser = it.data)
                    }
                    is Result.Error -> {
                        Napier.e("getSignedInUser: ${it.message}")
                    }
                    is Result.Loading -> {
                        Napier.d("getSignedInUser: Loading")
                    }
                    else -> Unit
                }
            }
        }
    }

    fun onSignIn() {
        with(_uiState.value) {
            if (!Validator.EmailValidator.validateAll(email)) {
                screenModelScope.launch {
                    val error = EmailError(getString(Res.string.invalid_email))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = NoError)
            }
            if (password.length < 6 || !Validator.PasswordValidator.validateAll(password)) {
                screenModelScope.launch {
                    val error = PasswordError(getString(Res.string.invalid_password))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = NoError)
            }
        }
        screenModelScope.launch {
            with(_uiState.value) {
                authRepo.signInWithEmail(email, password).collect { result ->
                    when(result){
                        is Result.SuccessWithData -> {
                            authRepo.setLocalUserId(result.data.id)
                            _uiState.update { it.copy(userId = result.data.id, signedInUser = result.data, error = NoError) }
                        }
                        is Result.Error -> {
                            _uiState.update {
                                it.copy(error = ServerError(result.message))
                            }
                        }
                        is Result.Loading -> {
                            Napier.d("signInWithOAuth: Loading")
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onLogOut() {
        authRepo.clearStorage()
    }

    fun signInWithOAuth(provider: OAuthProvider) {
        screenModelScope.launch(DispatcherIO) {
            authRepo.signInWithOAuth(provider).collect{ result ->
                when(result){
                    is Result.SuccessWithData -> {
                        authRepo.setLocalUserId(result.data.id)
                        _uiState.update { it.copy(userId = result.data.id, signedInUser = result.data, error = NoError) }
                    }
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(error = ServerError(result.message))
                        }
                    }
                    is Result.Loading -> {
                        Napier.d("signInWithOAuth: Loading")
                    }
                    else -> Unit
                }
            }
        }
    }
}