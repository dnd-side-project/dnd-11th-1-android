package com.materip.feature_home3.state


// TODO: 동행후기 ReviewUiState
sealed class ReviewUiState {
    data object Initial : ReviewUiState()
    data object Loading : ReviewUiState()
    data object Success : ReviewUiState()
    data class Error(val message: String) : ReviewUiState()
}
