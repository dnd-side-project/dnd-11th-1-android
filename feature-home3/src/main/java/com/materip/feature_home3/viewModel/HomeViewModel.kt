package com.materip.feature_home3.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.accompany_board.id.BoardIdDto
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

    private val _createdBoardId = MutableStateFlow<Int?>(null)
    val createdBoardId: StateFlow<Int?> = _createdBoardId

    private val _title = MutableStateFlow("")
    private val _content = MutableStateFlow("")
    private val _tagNames = MutableStateFlow(listOf<String>())
    private val _preferredGender = MutableStateFlow("")
    private val _preferredAge = MutableStateFlow("")
    private val _category = MutableStateFlow(listOf<String>())
    private val _region = MutableStateFlow("")
    private val _startDate = MutableStateFlow("")
    private val _endDate = MutableStateFlow("")
    private val _capacity = MutableStateFlow(2)
    private val _imageUris = MutableStateFlow(listOf<String>())

    fun onHomeIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.UpdateTitle -> updateTitle(intent.title)
            is HomeIntent.UpdateContent -> updateContent(intent.content)
            is HomeIntent.UpdateTagNames -> updateTags(intent.tagNames)
            is HomeIntent.UpdateRegion -> updateRegion(intent.region)
            is HomeIntent.UpdateStartDate -> updateStartDate(intent.startDate)
            is HomeIntent.UpdateEndDate -> updateEndDate(intent.endDate)
            is HomeIntent.UpdateCategory -> updateCategory(intent.category)
            is HomeIntent.UpdateAge -> updateAge(intent.preferredAge)
            is HomeIntent.UpdateGender -> updateGender(intent.preferredGender)
            is HomeIntent.UpdateCapacity -> updateCapacity(intent.capacity)
            is HomeIntent.UpdateImageUris -> updateImageUris(intent.imageUris)
            is HomeIntent.CreatePost -> createPost(toBoardRequestDto())
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

    private fun updateCategory(newCategory: List<String>) {
        _category.value = newCategory
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

    fun createPost(boardRequestDto: BoardRequestDto): BoardIdDto {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            val result = boardRepository.postBoard(boardRequestDto)
            val boardIdDto = result.data

            _uiState.value = if (boardIdDto != null) {
                _createdBoardId.value = boardIdDto.boardId
                HomeUiState.SuccessPost
            } else {
                HomeUiState.Error(result.error?.message ?: "게시글 작성에 실패했습니다.") // 에러 시 UI에 알림
            }
        }
        return BoardIdDto(boardId = _createdBoardId.value ?: 0)
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

    // TODO: HomeScreen에서 CompanionRounge에서 쓰일 함수, 동행글 상세 조회
    private fun loadBoardDetail(boardId: Int) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            val result = boardRepository.getBoardDetail(boardId)
            val boardDetail = result.data

            _uiState.value = if (boardDetail != null) {
                HomeUiState.SuccessLoad(boardDetail)
            } else {
                HomeUiState.Error(result.error?.message ?: "게시글 상세 정보를 불러오는데 실패했습니다.")
            }
        }
    }
}