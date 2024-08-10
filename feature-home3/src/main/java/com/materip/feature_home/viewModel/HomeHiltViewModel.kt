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

    private val _title = MutableStateFlow("")
    private val _content = MutableStateFlow("")
    private val _tags = MutableStateFlow(listOf<String>())
    private val _gender = MutableStateFlow("")
    private val _age = MutableStateFlow("")
    private val _type = MutableStateFlow("")
    private val _region = MutableStateFlow("")

    // 입력된 데이터를 업데이트하는 함수들
    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateContent(newContent: String) {
        _content.value = newContent
    }

    fun updateTags(newTags: List<String>) {
        _tags.value = newTags
    }

    fun updateGender(newGender: String) {
        _gender.value = newGender
    }

    fun updateAge(newAge: String) {
        _age.value = newAge
    }

    fun updateType(newType: String) {
        _type.value = newType
    }

    fun updateRegion(newRegion: String) {
        _region.value = newRegion
    }

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