package com.materip.feature_home3.state

import com.materip.core_model.accompany_board.id.BoardIdDto

sealed class PostBoardUiState {
    data object Initial : PostBoardUiState()
    data object Loading : PostBoardUiState()
    data class Success(val boardIdDto: BoardIdDto) : PostBoardUiState()
    data class Error(val message: String) : PostBoardUiState()
}
