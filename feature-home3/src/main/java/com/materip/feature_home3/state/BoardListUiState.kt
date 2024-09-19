package com.materip.feature_home3.state

import com.materip.core_model.accompany_board.all.BoardListResponse
import com.materip.core_model.accompany_board.search.SearchListResponse

sealed class BoardListUiState {
    data object Initial : BoardListUiState()
    data object Loading : BoardListUiState()
    data class Success(val boardList: BoardListResponse?) : BoardListUiState()
    data class SearchSuccess(val searchBoardList: SearchListResponse?) : BoardListUiState()
    data class Error(val message: String) : BoardListUiState()
}