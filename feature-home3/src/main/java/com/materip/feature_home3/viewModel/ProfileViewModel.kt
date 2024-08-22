package com.materip.feature_home3.viewModel

import com.materip.core_repository.repository.profile_repository.ProfileRepository
import com.materip.feature_home3.state.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.materip.core_model.response.GetProfileDetailsResponseDto
import com.materip.core_repository.repository.home_repository.BoardRepository
import com.materip.feature_home3.intent.ProfileIntent
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val boardRepository: BoardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Initial)
    val uiState: StateFlow<ProfileUiState> = _uiState

    private val _profileDetails = MutableStateFlow<GetProfileDetailsResponseDto?>(null)
    val profileDetails: StateFlow<GetProfileDetailsResponseDto?> = _profileDetails.asStateFlow()

    fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.GetProfileDetails -> getProfileDetails()
            is ProfileIntent.GetUserId -> getUserId(intent.boardId)
            is ProfileIntent.GetNickname -> getNickname(intent.userId)
        }
    }

    fun getProfileDetails() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val profileDetailsResult = profileRepository.getProfileDetails()
                if (profileDetailsResult.data != null) {
                    _uiState.value = ProfileUiState.Success(profileDetailsResult.data!!)
                } else {
                    _uiState.value = ProfileUiState.Error("프로필 로드 실패")
                }
            } catch (e: Exception) {
                _uiState.value =
                    ProfileUiState.Error(e.message ?: "프로필 로드 실패")
            }
        }
    }

    private fun getUserId(boardId: Int): Int {
        var userId: Int? = null
        viewModelScope.launch {
            val result = boardRepository.getBoardDetail(boardId)
            userId = result.data?.profileThumbnail?.userId
        }
        return userId ?: 0
    }

    private fun getNickname(userId: Int): String {
        var nickname: String? = null
        viewModelScope.launch {
            val result = boardRepository.getUserProfile()
            if (result.data?.userId == userId) {
                nickname = result.data!!.nickname
            }
        }
        return nickname ?: ""
    }
}
