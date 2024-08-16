package com.materip.feature_home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.accompany_board.BoardListResponse
import com.materip.core_model.accompany_board.Pageable
import com.materip.core_repository.repository.BoardRepository
import com.materip.feature_home.state.BoardListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val boardRepository: BoardRepository
) : ViewModel() {

    // UI 상태를 관리하는 StateFlow
    private val _uiState = MutableStateFlow<BoardListUiState>(BoardListUiState.Initial)
    val uiState: StateFlow<BoardListUiState> = _uiState

    // 게시판 목록을 관리하는 StateFlow
    private val _boardList = MutableStateFlow(BoardListResponse(false, emptyList()))
    val boardList: StateFlow<BoardListResponse> = _boardList

    // 게시판 목록 아이템 상태를 관리하는 StateFlow
    private val _pageable = MutableStateFlow(Pageable(0, 10, emptyList()))
    val pageable: StateFlow<Pageable> = _pageable


    // 게시판 목록 로드 함수
    private fun loadBoardList(pageable: Pageable) {
        viewModelScope.launch {
            _uiState.value = BoardListUiState.Loading // 로딩 상태로 UI 상태 변경

            val result = boardRepository.getBoard(pageable) // 데이터 요청
            val boardListData = result.data

            _uiState.value = if (boardListData != null) {
                _boardList.value = boardListData // _boardList 갱신
                BoardListUiState.Success(boardListData) // 성공 상태로 UI 상태 변경
            } else {
                BoardListUiState.Error(result.error?.message ?: "동행글 조회 실패") // 오류 상태로 UI 상태 변경
            }
        }
    }
}