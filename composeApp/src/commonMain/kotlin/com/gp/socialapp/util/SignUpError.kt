package com.gp.socialapp.util

sealed class SignUpError {
    data class EmailError(var message: String) : SignUpError()
    data class PasswordError(var message: String) : SignUpError()
    data class RePasswordError(var message: String) : SignUpError()
    data object NoError : SignUpError()
}