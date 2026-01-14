package com.yangpixi.rememberdrinking.presentation.screen.UiState

/**
 * @author yangpixi
 * @date 2026/1/14 15:25
 * @description
 */

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val exception: Exception) : UiState<Nothing>()
}