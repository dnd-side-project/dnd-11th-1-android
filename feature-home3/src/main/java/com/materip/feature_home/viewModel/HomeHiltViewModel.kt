package com.materip.feature_home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.BoardRequestDto
import com.materip.core_repository.repository.BoardRepository
import com.materip.feature_home.intent.HomeIntent
import com.materip.feature_home.state.HomeUiState
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
    private val _startDate = MutableStateFlow("")
    private val _endDate = MutableStateFlow("")
    private val _capacity = MutableStateFlow(2)

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.UpdateTitle -> updateTitle(intent.title)
            is HomeIntent.UpdateContent -> updateContent(intent.content)
            is HomeIntent.UpdateTags -> updateTags(intent.tags)
            is HomeIntent.UpdateRegion -> updateRegion(intent.region)
            is HomeIntent.UpdateStartDate -> updateStartDate(intent.startDate)
            is HomeIntent.UpdateEndDate -> updateEndDate(intent.endDate)
            is HomeIntent.UpdateType -> updateType(intent.type)
            is HomeIntent.UpdateAge -> updateAge(intent.age)
            is HomeIntent.UpdateGender -> updateGender(intent.gender)
            is HomeIntent.UpdateCapacity -> updateCapacity(intent.capacity)
        }
    }

    // 입력된 데이터를 업데이트하는 함수들
    private fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    private fun updateContent(newContent: String) {
        _content.value = newContent
    }

    private fun updateTags(newTags: List<String>) {
        _tags.value = newTags
    }

    private fun updateGender(newGender: String) {
        _gender.value = newGender
    }

    private fun updateAge(newAge: String) {
        _age.value = newAge
    }

    private fun updateType(newType: String) {
        _type.value = newType
    }

    private fun updateRegion(newRegion: String) {
        _region.value = newRegion
    }

    private fun updateStartDate(newStartDate: String) {
        _startDate.value = newStartDate
    }

    private fun updateEndDate(newEndDate: String) {
        _endDate.value = newEndDate
    }

    private fun updateCapacity(newCapacity: Int) {
        _capacity.value = newCapacity
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