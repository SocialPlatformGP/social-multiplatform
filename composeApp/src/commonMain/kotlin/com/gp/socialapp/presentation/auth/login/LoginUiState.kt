package com.gp.socialapp.presentation.auth.login

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.AuthError
import com.gp.socialapp.util.Result


data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var error: AuthError = AuthError.NoError,
    var token: String? = null,
)
