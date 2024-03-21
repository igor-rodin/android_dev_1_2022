package ru.igor.rodin.retrofit.data

sealed class ProgressState(open val isLoading: Boolean = false) {
    data class Loading(override val isLoading: Boolean = true) : ProgressState(isLoading)
    data class Success(override val isLoading: Boolean = false) : ProgressState(isLoading)
    data class Error(val message: String, override val isLoading: Boolean = false) :
        ProgressState(isLoading)
}