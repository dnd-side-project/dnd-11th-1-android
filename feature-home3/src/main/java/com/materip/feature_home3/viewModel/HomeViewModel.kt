package com.materip.feature_home3.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_repository.repository.home_repository.BoardRepository
import com.materip.feature_home3.intent.HomeIntent
import com.materip.feature_home3.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val boardRepository: BoardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Initial)
    val uiState: StateFlow<HomeUiState> = _uiState

    val showDialogState = mutableStateOf(false)

    fun onHomeIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadBoardDetail -> loadBoardDetail(intent.boardId)
            is HomeIntent.DeleteBoard -> deleteBoard(intent.boardId)
        }
    }

    private fun loadBoardDetail(boardId: Int) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            val result = boardRepository.getBoardDetail(boardId)
            val boardDetail = result.data

            _uiState.value = if (boardDetail != null) {
                HomeUiState.SuccessLoad(boardDetail)
            } else {
                HomeUiState.Error(result.error?.message ?: "동행글 상세 정보를 불러오는데 실패했습니다.")
            }
        }
    }

    // 동행글 삭제하는 로직
    private fun deleteBoard(boardId: Int) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            val result = boardRepository.deleteBoard(boardId)
            if (result.data != null) {
                _uiState.value = HomeUiState.SuccessDelete
            } else {
                _uiState.value = HomeUiState.Error(result.error?.message ?: "동행글 삭제에 실패했습니다.")
            }
        }
    }
}