package com.materip.feature_home3.viewModel

import androidx.lifecycle.SavedStateHandle
import com.materip.core_repository.repository.profile_repository.ProfileRepository
import com.materip.feature_home3.state.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.materip.core_model.response.GetProfileDetailsResponseDto
import com.materip.core_repository.repository.home_repository.BoardRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val boardRepository: BoardRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val boardId: Int = savedStateHandle["boardId"] ?: 0

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _profileDetails = MutableStateFlow<GetProfileDetailsResponseDto?>(null)
    val profileDetails: StateFlow<GetProfileDetailsResponseDto?> = _profileDetails.asStateFlow()

    private val _userNickname = MutableStateFlow<String>("")
    val userNickname: StateFlow<String> = _userNickname.asStateFlow()

    private val _boardUserId = MutableStateFlow<Int>(0)
    val boardUserId: StateFlow<Int> = _boardUserId.asStateFlow()

    private val _loggedInUserId = MutableStateFlow<Int?>(null)
    val loggedInUserId: StateFlow<Int?> = _loggedInUserId.asStateFlow()

    init {
        viewModelScope.launch {
            val nickname = getNicknameFromBoard(boardId)
            _userNickname.value = nickname

            val userId = getUserIdFromBoard(boardId)
            _boardUserId.value = userId

            val profileDetailsResult = profileRepository.getProfileDetails()
            _loggedInUserId.value = profileDetailsResult.data?.userId

            getProfileDetails(userId)
        }
    }

    // 동행글 작성자의 상제 정보를 가져옴
    private suspend fun getProfileDetails(userId: Int) {
        _uiState.value = ProfileUiState.Loading
        try {
            val profileDetailsResult = profileRepository.getProfileDetails()
            if (profileDetailsResult.data != null && profileDetailsResult.data!!.userId == userId) {
                _profileDetails.value = profileDetailsResult.data
                _uiState.value = ProfileUiState.Success(profileDetailsResult.data!!)
            } else {
                _uiState.value = ProfileUiState.Error("동행글 유저의 프로필 상세 정보 조회 실패")
            }
        } catch (e: Exception) {
            _uiState.value = ProfileUiState.Error(e.message ?: "동행글 유저의 프로필 상세 정보 조회 실패")
        }
    }

    private suspend fun getNicknameFromBoard(boardId: Int): String {
        val result = boardRepository.getBoardDetail(boardId)
        return result.data?.profileThumbnail?.nickname ?: ""
    }

    private suspend fun getUserIdFromBoard(boardId: Int): Int {
        val result = boardRepository.getBoardDetail(boardId)
        return result.data?.profileThumbnail?.userId ?: 0
    }
}
