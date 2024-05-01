package com.gp.socialapp.presentation.auth.login

import com.gp.socialapp.data.auth.source.remote.model.User
import com.gp.socialapp.presentation.auth.util.AuthError


data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var error: AuthError = AuthError.NoError,
    var signedInUser: User? = null,
    var userId: String = "",
    val theme: String = "System Default",
    var isDone: Boolean = false,
)
