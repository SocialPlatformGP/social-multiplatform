package com.gp.socialapp.presentation.auth.signup

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.AuthError

data class SignUpUiState(
    var email: String = "",
    var password: String = "",
    var rePassword: String = "",
    var error: AuthError = AuthError.NoError,
    var isSignedUp: Result<User> = Result.Idle,
)
