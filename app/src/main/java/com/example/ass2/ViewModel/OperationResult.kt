package com.example.ass2.ViewModel

sealed class OperationResult<out T> {
    data class Success<out T>(val data: T) : OperationResult<T>()
    data class Failure(val exception: Exception) : OperationResult<Nothing>()

    fun <R> fold(onSuccess: (T) -> R, onFailure: (Exception) -> R): R = when (this) {
        is Success -> onSuccess(data)
        is Failure -> onFailure(exception)
    }
}
