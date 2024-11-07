package com.materip.feature_home3.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.accompany_board.all.BoardListResponse
import com.materip.core_model.accompany_board.search.QueryRequestDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_repository.repository.home_repository.BoardRepository
import com.materip.feature_home3.BuildConfig
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

    private val serverBaseUrl = BuildConfig.SERVER_BASE_URL

    private fun loadBoardList(pagingRequestDto: PagingRequestDto) {
        viewModelScope.launch {
            _uiState.value = BoardListUiState.Loading

            try {
                val result = boardRepository.getBoard(pagingRequestDto)
                val boardListResponse = result.data
                Log.d("BoardViewModel", "API response: $boardListResponse")

                val updatedBoardList = boardListResponse?.copy(
                    data = boardListResponse.data.map { boardItem ->
                        boardItem.copy(
                            imageUrls = boardItem.imageUrls.map { path ->
                                if (path.startsWith("http")) {
                                    path
                                } else {
                                    "$serverBaseUrl$path"
                                }
                            }
                        )
                    }
                )

                val currentData = _boardList.value?.data ?: emptyList()
                val newData = (currentData + (boardListResponse?.data ?: emptyList())).distinctBy { it.boardId }

                _boardList.value = updatedBoardList?.copy(
                    data = newData,
                    cursor = updatedBoardList.cursor ?: _boardList.value?.cursor,
                    hasNext = updatedBoardList.hasNext
                )

                _uiState.value = BoardListUiState.Success(updatedBoardList)
            } catch (e: Exception) {
                _uiState.value = BoardListUiState.Error(e.message ?: "동행글 목록 로드 실패")
            }
        }
    }

    // 검색 메서드 추가
    private fun searchBoardList(query: QueryRequestDto) {
        viewModelScope.launch {
            Log.d("BoardViewModel", "QueryRequestDto: $query")
            _uiState.value = BoardListUiState.Loading

            try {
                val result = boardRepository.searchBoardList(query)
                Log.d("BoardViewModel", "Search API result: $result")

                val searchBoardData = result.data
                Log.d("BoardViewModel", "Parsed search data: $searchBoardData")

                if (searchBoardData != null) {
                    _boardList.value = searchBoardData
                    _uiState.value = BoardListUiState.Success(searchBoardData)
                } else {
                    _uiState.value = BoardListUiState.Error("검색 결과가 없습니다.")
                }
            } catch (e: Exception) {
                _uiState.value = BoardListUiState.Error("동행글 검색 실패")
            }
        }
    }

    // 동행 시작 여부에 따른 동행글 목록 조회
    private fun filteredBoardList() {

    }

    fun handleIntent(intent: BoardListIntent) {
        when (intent) {
            is BoardListIntent.LoadBoardList -> {
                Log.d(
                    "BoardViewModel",
                    "Loading board list with cursor: ${intent.pagingRequestDto.cursor}")
                loadBoardList(intent.pagingRequestDto)
            }
            is BoardListIntent.SearchBoardList -> {
                Log.d("BoardViewModel",
                    "Searching board list with query: ${intent.query}")

                searchBoardList(intent.query)
            }
        }
    }
}