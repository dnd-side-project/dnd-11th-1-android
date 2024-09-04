package com.materip.feature_home3.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.accompany_board.all.BoardListResponse
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_repository.repository.home_repository.BoardRepository
import com.materip.feature_home3.intent.BoardListIntent
import com.materip.feature_home3.state.BoardListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val boardRepository: BoardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BoardListUiState>(BoardListUiState.Initial)
    val uiState: StateFlow<BoardListUiState> = _uiState

    private val _boardList = MutableStateFlow<BoardListResponse?>(null)
    val boardList: StateFlow<BoardListResponse?> = _boardList

    private fun loadBoardList(pagingRequestDto: PagingRequestDto) {
        viewModelScope.launch {
            _uiState.value = BoardListUiState.Loading

            try {
                val result = boardRepository.getBoard(pagingRequestDto)

                val boardListResponse = result.data
                val currentData = _boardList.value?.data ?: emptyList()
                val newData = currentData + (boardListResponse?.data ?: emptyList())

                val updatedBoardList = boardListResponse?.copy(data = newData)
                _boardList.value = updatedBoardList

                _uiState.value = BoardListUiState.Success(updatedBoardList)
            } catch (e: Exception) {
                _uiState.value = BoardListUiState.Error(e.message ?: "동행글 목록 로드 실패")
            }
        }
    }

    fun handleIntent(intent: BoardListIntent) {
        when (intent) {
            is BoardListIntent.LoadBoardList -> loadBoardList(intent.pagingRequestDto)
        }
    }
}