package com.gp.socialapp.presentation.auth.passwordreset

import com.gp.socialapp.util.Result

data class PasswordResetUiState(
    var email: String = "",
    var sentState: Result<Nothing> = Result.Idle
)