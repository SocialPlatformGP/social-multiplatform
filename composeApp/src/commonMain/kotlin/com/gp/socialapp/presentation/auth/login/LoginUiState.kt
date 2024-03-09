package com.gp.socialapp.presentation.auth.login

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.util.Result


data class LoginUiState(
    var email: String = "",
    var emailError: String = "",
    var password: String = "",
    var passwordError: String = "",
    var signInResult: Result<User> = Result.Idle,
)
