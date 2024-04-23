package com.gp.socialapp.presentation.auth.signup

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.auth.util.AuthError
import com.gp.socialapp.util.Result

data class SignUpUiState(
    var email: String = "",
    var password: String = "",
    var rePassword: String = "",
    val signedUpUser: User? = null,
    var error: AuthError = AuthError.NoError,
)
