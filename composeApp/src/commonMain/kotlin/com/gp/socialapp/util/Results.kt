package com.gp.socialapp.util

typealias RootError = Error

sealed interface Results<out D, out E : RootError> {
    data object Loading : Results<Nothing, Nothing>
    data class Success<out D>(val data: D) : Results<D, Nothing>
    data class Failure<out E : RootError>(val error: E) : Results<Nothing, E>
}
