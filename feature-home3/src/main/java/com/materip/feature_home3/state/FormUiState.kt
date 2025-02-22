package com.materip.feature_home3.state

sealed class FormUiState {
    data object Initial : FormUiState()
    data object Loading : FormUiState()
    data object Success : FormUiState()
    data class Error(val message: String) : FormUiState()
}