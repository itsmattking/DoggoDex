package me.mking.doggodex.data

sealed class DataResult<T> {
    data class Error<T>(val message: String) : DataResult<T>()
    data class Success<T>(val data: T) : DataResult<T>()
}