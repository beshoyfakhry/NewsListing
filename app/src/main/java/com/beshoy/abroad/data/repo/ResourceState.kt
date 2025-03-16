package com.beshoy.abroad.data.repo

sealed interface ResourceState {
    data class Success<T>(val data: T) : ResourceState
    data class Error(val message: String) : ResourceState
    data object Loading : ResourceState
}