package com.materip.feature_home.state

import com.materip.core_model.accompany_board.BoardListResponse

sealed class BoardListUiState {
    data object Initial : BoardListUiState()
    data object Loading : BoardListUiState()
    data class Success(val boardList: BoardListResponse) : BoardListUiState()
    data class Error(val message: String) : BoardListUiState()
}