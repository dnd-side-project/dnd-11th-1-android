package com.materip.feature_home3.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.accompany_board.create.BoardStatus
import com.materip.core_model.accompany_board.create.Category
import com.materip.core_model.accompany_board.create.PreferredAge
import com.materip.core_model.accompany_board.create.PreferredGender
import com.materip.core_model.accompany_board.create.Region
import com.materip.core_repository.repository.home_repository.BoardRepository
import com.materip.feature_home3.intent.PostBoardIntent
import com.materip.feature_home3.state.PostBoardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostBoardViewModel @Inject constructor(
    private val boardRepository: BoardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PostBoardUiState>(PostBoardUiState.Initial)
    val uiState: StateFlow<PostBoardUiState> = _uiState

    private val _createdBoardIds = MutableStateFlow<List<Int>>(emptyList())
    val createdBoardIds: StateFlow<List<Int>> = _createdBoardIds

    private val _title = MutableStateFlow("")
    private val _content = MutableStateFlow("")
    private val _tagNames = MutableStateFlow(listOf<String>())
    private val _preferredGender = MutableStateFlow(PreferredGender.ANY)
    private val _preferredAge = MutableStateFlow(PreferredAge.ANY)
    private val _category = MutableStateFlow<List<Category>>(emptyList())
    private val _region = MutableStateFlow(Region.SEOUL)
    private val _startDate = MutableStateFlow("")
    private val _endDate = MutableStateFlow("")
    private val _capacity = MutableStateFlow(2)
    private val _imageUris = MutableStateFlow(listOf<String>())
    private val _boardStatus = MutableStateFlow(BoardStatus.RECRUITING)

    fun handleIntent(intent: PostBoardIntent) {
        when (intent) {
            is PostBoardIntent.UpdateTitle -> updateTitle(intent.title)
            is PostBoardIntent.UpdateContent -> updateContent(intent.content)
            is PostBoardIntent.UpdateTagNames -> updateTags(intent.tagNames)
            is PostBoardIntent.UpdateRegion -> updateRegion(intent.region)
            is PostBoardIntent.UpdateStartDate -> updateStartDate(intent.startDate)
            is PostBoardIntent.UpdateEndDate -> updateEndDate(intent.endDate)
            is PostBoardIntent.UpdateCategory -> updateCategory(intent.category)
            is PostBoardIntent.UpdateAge -> updateAge(intent.preferredAge)
            is PostBoardIntent.UpdateGender -> updateGender(intent.preferredGender)
            is PostBoardIntent.UpdateCapacity -> updateCapacity(intent.capacity)
            is PostBoardIntent.UpdateImageUris -> updateImageUris(intent.imageUris)
            is PostBoardIntent.UpdateBoardStatus -> updateBoardStatus(intent.boardStatus)
            is PostBoardIntent.CreatePost -> createPost(toBoardRequestDto())
        }
    }

    private fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    private fun updateContent(newContent: String) {
        _content.value = newContent
    }

    private fun updateTags(newTags: List<String>) {
        _tagNames.value = newTags
    }

    private fun updateGender(newGender: PreferredGender) {
        _preferredGender.value = newGender
    }

    private fun updateAge(newAge: PreferredAge) {
        _preferredAge.value = newAge
    }

    private fun updateCategory(newCategory: List<Category>) {
        _category.value = newCategory
    }

    private fun updateRegion(newRegion: Region) {
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

    private fun updateBoardStatus(newBoardStatus: BoardStatus) {
        _boardStatus.value = newBoardStatus
    }

    fun createPost(boardRequestDto: BoardRequestDto) {
        viewModelScope.launch {
            _uiState.value = PostBoardUiState.Loading

            val result = boardRepository.postBoard(boardRequestDto)
            val boardIdDto = result.data

            _uiState.value = if (boardIdDto != null) {
                _createdBoardIds.value += boardIdDto.boardId
                PostBoardUiState.Success
            } else {
                PostBoardUiState.Error(result.error?.message ?: "게시글 작성에 실패했습니다.")
            }
        }
    }

    fun toBoardRequestDto(): BoardRequestDto {
        return BoardRequestDto(
            title = _title.value,
            content = _content.value,
            region = _region.value,
            startDate = _startDate.value,
            endDate = _endDate.value,
            capacity = _capacity.value,
            boardStatus = _boardStatus.value,
            categories = _category.value,
            preferredAge = _preferredAge.value,
            preferredGender = _preferredGender.value,
            imageUrls = _imageUris.value,
            tagNames = _tagNames.value
        )
    }
}