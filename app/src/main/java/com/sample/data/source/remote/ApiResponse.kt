package com.sample.data.source.remote

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Throwable) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}