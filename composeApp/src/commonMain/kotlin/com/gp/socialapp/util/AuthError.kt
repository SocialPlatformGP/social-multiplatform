package com.gp.socialapp.util

sealed class AuthError {
    data class EmailError(var message: String) : AuthError()
    data class PasswordError(var message: String) : AuthError()
    data class RePasswordError(var message: String) : AuthError()
    data class ServerError(var message: String) : AuthError()
    data object NoError : AuthError()
}