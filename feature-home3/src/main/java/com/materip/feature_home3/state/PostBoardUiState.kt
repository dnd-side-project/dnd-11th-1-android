package com.materip.feature_home3.state

sealed class PostBoardUiState {
    data object Initial : PostBoardUiState()
    data object Loading : PostBoardUiState()
    data object Success : PostBoardUiState()
    data class Error(val message: String) : PostBoardUiState()
}
