package com.materip.feature_home3.state

import com.materip.core_model.accompany_board.id.GetBoardDetailDto

sealed class HomeUiState {
    data object Initial : HomeUiState()
    data object Loading : HomeUiState()
    data object SuccessPost : HomeUiState()
    data class SuccessLoad(val boardDetail: GetBoardDetailDto) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}