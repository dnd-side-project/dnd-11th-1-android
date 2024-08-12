package com.materip.feature_home.state

sealed class HomeUiState {
    data object Initial : HomeUiState()
    data object Loading : HomeUiState()
    data object Success : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}