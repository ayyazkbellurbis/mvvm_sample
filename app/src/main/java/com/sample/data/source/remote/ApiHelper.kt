package com.sample.data.source.remote

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResponse<T> {
    return try {
        ApiResponse.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> ApiResponse.Error(IOException("Network Error", throwable))
            is HttpException -> {
                val code = throwable.code()
                val errorResponse = parseErrorResponse(throwable)
                ApiResponse.Error(HttpExceptionWithCode(throwable, code, errorResponse))
            }
            else -> ApiResponse.Error(Exception("Unexpected Error", throwable))
        }
    }
}

private fun parseErrorResponse(throwable: HttpException): String {
    return throwable.response()?.errorBody()?.string() ?: "Unknown error"
}

data class HttpExceptionWithCode(
    val httpException: HttpException,
    val code: Int,
    val errorResponse: String
) : Exception()