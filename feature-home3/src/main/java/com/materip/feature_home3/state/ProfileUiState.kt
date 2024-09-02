package com.materip.feature_home3.state

import com.materip.core_model.response.GetProfileDetailsResponseDto

sealed class ProfileUiState {
    data object Initial : ProfileUiState()
    data object Loading : ProfileUiState()
    data class Success(val profileDetails: GetProfileDetailsResponseDto) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}