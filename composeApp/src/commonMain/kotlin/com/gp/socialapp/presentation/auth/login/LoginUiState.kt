package com.gp.socialapp.presentation.auth.login

import com.gp.socialapp.presentation.auth.util.AuthError


data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var error: AuthError = AuthError.NoError,
    var token: String? = null,
)
