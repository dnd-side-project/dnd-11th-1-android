package com.materip.feature_home3.viewModel

import com.materip.core_repository.repository.profile_repository.ProfileRepository
import com.materip.feature_home3.state.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.materip.core_model.response.GetProfileDetailsResponseDto
import com.materip.feature_home3.intent.ProfileIntent
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Initial)
    val uiState: StateFlow<ProfileUiState> = _uiState

    private val _profileDetails = MutableStateFlow<GetProfileDetailsResponseDto?>(null)
    val profileDetails: StateFlow<GetProfileDetailsResponseDto?> = _profileDetails.asStateFlow()

    fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.GetProfileDetails -> getProfileDetails()
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
}
