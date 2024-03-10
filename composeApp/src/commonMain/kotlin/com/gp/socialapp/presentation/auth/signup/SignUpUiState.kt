package com.gp.socialapp.presentation.auth.signup

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.SignUpError

data class SignUpUiState(
    var email: String = "",
    var password: String = "",
    var rePassword: String = "",
    var error: SignUpError = SignUpError.NoError,
    var isSignedUp: Result<User> = Result.Idle,
    var userExists: Result<Boolean> = Result.Idle
)
