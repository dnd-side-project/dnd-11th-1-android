package com.materip.feature_home3.state

import com.materip.core_model.accompany_board.profile.GetUserProfile

sealed class ProfileUiState {
    data object Initial : ProfileUiState()
    data object Loading : ProfileUiState()
    data class Success(val profileDetails: GetUserProfile) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}