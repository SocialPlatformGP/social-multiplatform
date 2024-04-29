package com.gp.socialapp.util

import kotlinx.serialization.Serializable

typealias RootError = Error

@Serializable
sealed interface Results<out D, out E : RootError> {
    @Serializable
    data object Loading : Results<Nothing, Nothing>

    @Serializable
    data class Success<out D>(val data: D) : Results<D, Nothing>

    @Serializable
    data class Failure<out E : RootError>(val error: E) : Results<Nothing, E>

    fun isSuccessful(): Boolean = this is Success

    companion object {
        fun <D> success(data: D): Results<D, Nothing> = Success(data)
        fun <E : RootError> failure(error: E): Results<Nothing, E> = Failure(error)
    }
}
