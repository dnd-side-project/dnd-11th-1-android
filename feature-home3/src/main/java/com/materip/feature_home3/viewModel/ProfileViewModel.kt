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

    init {
        viewModelScope.launch {
            getProfileDetails()
            val nickname = getNicknameFromBoard(boardId)
            _userNickname.value = nickname
        }
    }

    private suspend fun getProfileDetails() {
        _uiState.value = ProfileUiState.Loading
        try {
            val profileDetailsResult = profileRepository.getProfileDetails()
            if (profileDetailsResult.data != null) {
                _profileDetails.value = profileDetailsResult.data
                _uiState.value = ProfileUiState.Success(profileDetailsResult.data!!)
            } else {
                _uiState.value = ProfileUiState.Error("프로필 로드 실패")
            }
        } catch (e: Exception) {
            _uiState.value = ProfileUiState.Error(e.message ?: "프로필 로드 실패")
        }
    }

    private suspend fun getNicknameFromBoard(boardId: Int): String {
        val result = boardRepository.getBoardDetail(boardId)
        return result.data?.profileThumbnail?.nickname ?: ""
    }
}
