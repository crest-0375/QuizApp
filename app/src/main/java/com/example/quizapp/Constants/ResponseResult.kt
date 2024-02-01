package com.example.quizapp.Constants

sealed class ResponseResult<out T> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Failure(val message: String) : ResponseResult<Nothing>()
    data class Loading(val isLoading: Boolean) : ResponseResult<Nothing>()
}