package com.materip.feature_home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.accompany_board.BoardRequestDto
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

    private val _createdBoardId = MutableStateFlow<Int?>(null)
    val createdBoardId: StateFlow<Int?> = _createdBoardId

    private val _title = MutableStateFlow("")
    private val _content = MutableStateFlow("")
    private val _tagNames = MutableStateFlow(listOf<String>())
    private val _preferredGender = MutableStateFlow("")
    private val _preferredAge = MutableStateFlow("")
    private val _category = MutableStateFlow("")
    private val _region = MutableStateFlow("")
    private val _startDate = MutableStateFlow("")
    private val _endDate = MutableStateFlow("")
    private val _capacity = MutableStateFlow(2)
    private val _imageUris = MutableStateFlow(listOf<String>())

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.UpdateTitle -> updateTitle(intent.title)
            is HomeIntent.UpdateContent -> updateContent(intent.content)
            is HomeIntent.UpdateTagNames -> updateTags(intent.tagNames)
            is HomeIntent.UpdateRegion -> updateRegion(intent.region)
            is HomeIntent.UpdateStartDate -> updateStartDate(intent.startDate)
            is HomeIntent.UpdateEndDate -> updateEndDate(intent.endDate)
            is HomeIntent.UpdateCategory -> updateType(intent.category)
            is HomeIntent.UpdateAge -> updateAge(intent.preferredAge)
            is HomeIntent.UpdateGender -> updateGender(intent.preferredGender)
            is HomeIntent.UpdateCapacity -> updateCapacity(intent.capacity)
            is HomeIntent.UpdateImageUris -> updateImageUris(intent.imageUris)
            is HomeIntent.LoadBoardDetail -> loadBoardDetail(intent.boardId)
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
        _tagNames.value = newTags
    }

    private fun updateGender(newGender: String) {
        _preferredGender.value = newGender
    }

    private fun updateAge(newAge: String) {
        _preferredAge.value = newAge
    }

    private fun updateType(newType: String) {
        _category.value = newType
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

    private fun updateImageUris(newImageUris: List<String>) {
        _imageUris.value = newImageUris
    }

    fun createPost(boardRequestDto: BoardRequestDto) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading // 서버와의 통신 시작을 알림

            val result = boardRepository.postBoard(boardRequestDto) // 서버에 BoardRequestDto 전송
            val boardIdDto = result.data // 로컬 변수로 할당

            _uiState.value = if (boardIdDto != null) {
                _createdBoardId.value = boardIdDto.boardId // 서버로부터 받은 boardId를 저장
                HomeUiState.SuccessPost // UI에 성공 상태 알림
            } else {
                HomeUiState.Error(result.error?.errMsg ?: "게시글 작성에 실패했습니다.") // 에러 시 UI에 알림
            }
        }
    }

    fun toBoardRequestDto(): BoardRequestDto {
        return BoardRequestDto(
            title = _title.value,
            content = _content.value,
            tagNames = _tagNames.value,
            region = _region.value,
            startDate = _startDate.value,
            endDate = _endDate.value,
            category = _category.value,
            preferredAge = _preferredAge.value,
            preferredGender = _preferredGender.value,
            capacity = _capacity.value,
            imageUrls = _imageUris.value
        )
    }

    private fun loadBoardDetail(boardId: Int) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            val result = boardRepository.getBoardDetail(boardId)
            val boardDetail = result.data

            _uiState.value = if (boardDetail != null) {
                HomeUiState.SuccessLoad(boardDetail)
            } else {
                HomeUiState.Error(result.error?.errMsg ?: "게시글 상세 정보를 불러오는데 실패했습니다.")
            }
        }
    }
}