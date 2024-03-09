package com.gp.socialapp.util

sealed class Result<out T> {
    object Success : Result<Nothing>()
    data class SuccessWithData<T>(var data: T) : Result<T>()
    data class Error<T>(var message: String) : Result<T>()
    object Loading : Result<Nothing>()
    object Idle : Result<Nothing>()
}
