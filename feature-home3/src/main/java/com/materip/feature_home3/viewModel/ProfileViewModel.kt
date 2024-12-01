package com.materip.feature_home3.viewModel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.materip.core_repository.repository.profile_repository.ProfileRepository
import com.materip.feature_home3.state.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.materip.core_model.accompany_board.profile.GetUserProfile
import com.materip.core_repository.repository.home_repository.BoardRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val boardRepository: BoardRepository,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext context: Context
) : ViewModel() {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val boardId: Int = savedStateHandle["boardId"] ?: 0

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _profileDetails = MutableStateFlow<GetUserProfile?>(null)
    val profileDetails: StateFlow<GetUserProfile?> = _profileDetails.asStateFlow()

    private val _userNickname = MutableStateFlow<String>("")
    val userNickname: StateFlow<String> = _userNickname.asStateFlow()

    private val _boardUserId = MutableStateFlow<Int>(0)
    val boardUserId: StateFlow<Int> = _boardUserId.asStateFlow()

    private val _loggedInUserId = MutableStateFlow<Int?>(null)
    val loggedInUserId: StateFlow<Int?> = _loggedInUserId.asStateFlow()

    private val _isApplicationCompleted = MutableStateFlow(false)
    val isApplicationCompleted: StateFlow<Boolean> = _isApplicationCompleted.asStateFlow()

    init {
        viewModelScope.launch {
            val nickname = getNicknameFromBoard(boardId)
            _userNickname.value = nickname

            val userId = getUserIdFromBoard(boardId)
            _boardUserId.value = userId

            getProfileDetails(userId)

            getLoggedInUserDetails()
        }
    }

    private suspend fun getProfileDetails(userId: Int) {
        _uiState.value = ProfileUiState.Loading
        try {
            val profileDetailsResult = boardRepository.getUserProfile(userId)
            Log.d("ProfileViewModel", "API Response: $profileDetailsResult")

            if (profileDetailsResult.data?.userId == userId) {
                _profileDetails.value = profileDetailsResult.data
                _uiState.value = ProfileUiState.Success(profileDetailsResult.data!!)
            } else {
                Log.e("ProfileViewModel", "userId does not match: API userId = ${profileDetailsResult.data?.userId}, expected userId = $userId")
                _uiState.value = ProfileUiState.Error("해당 유저의 프로필을 가져올 수 없습니다.")
            }
        } catch (e: Exception) {
            Log.e("ProfileViewModel", "API call failed: ${e.message}")
            _uiState.value = ProfileUiState.Error(e.message ?: "프로필 로드 중 오류 발생: ${e.message}")
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

    private suspend fun getLoggedInUserDetails() {
        val profileDetailsResult = profileRepository.getProfileDetails()
        _loggedInUserId.value = profileDetailsResult.data?.userId
    }

    fun isApplicationCompleted(boardId: Int): Boolean {
        return sharedPreferences.getBoolean("application_completed_$boardId", false)
    }

    fun completeApplication(boardId: Int) {
        sharedPreferences.edit().putBoolean("application_completed_$boardId", true).apply()
    }
}
