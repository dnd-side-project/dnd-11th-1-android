package com.materip.feature_home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.BoardRequestDto
import com.materip.core_repository.repository.BoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeHiltViewModel @Inject constructor(
    private val boardRepository: BoardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Initial)
    val uiState: StateFlow<HomeUiState> = _uiState

    fun createPost(boardRequestDto: BoardRequestDto) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            val result = boardRepository.postBoard(boardRequestDto)
            _uiState.value = if (result.data != null) {
                HomeUiState.Success
            } else {
                HomeUiState.Error(result.error?.errMsg ?: "게시글 작성에 실패했습니다.")
            }
        }
    }
}

sealed class HomeUiState {
    data object Initial : HomeUiState()
    data object Loading : HomeUiState()
    data object Success : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}