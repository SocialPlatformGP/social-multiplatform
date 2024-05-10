package com.gp.socialapp.util
//
//sealed class Result<out T> {
//    object Success : Result<Nothing>()
//    data class Success<T>(var data: T) : Result<T>()
//    data class Error<T>(var message: String) : Result<T>()
//    object Loading : Result<Nothing>()
//    object Idle : Result<Nothing>()
//
//
//    fun onSuccessWithData(block: (T) -> Unit): Result<T> {
//        if (this is Success) {
//            block(data)
//        }
//        return this
//    }
//
//    fun onSuccess(block: () -> Unit): Result<T> {
//        if (this is Success) {
//            block()
//        }
//        return this
//    }
//
//    fun onLoading(block: () -> Unit): Result<T> {
//        if (this is Loading) {
//            block()
//        }
//        return this
//    }
//
//
//    fun onFailure(block: (String) -> Unit): Result<T> {
//        if (this is Error) {
//            block(message)
//        }
//        return this
//    }
//}
