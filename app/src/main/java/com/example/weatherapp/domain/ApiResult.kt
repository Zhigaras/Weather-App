package com.example.weatherapp.domain

enum class ApiStatus{
    SUCCESS,
    ERROR,
    LOADING
}

sealed class ApiResult <out T> (val status: ApiStatus, val data: T?, val message:String?) {
    
    data class Success<out R>(val _data: R?): ApiResult<R>(
        status = ApiStatus.SUCCESS,
        data = _data,
        message = null
    )
    
    data class Error(val exception: String): ApiResult<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        message = exception
    )
    
    class Loading<out R> : ApiResult<R>(
        status = ApiStatus.LOADING,
        data = null,
        message = null
    )
}