package com.materip.feature_home.state

import com.materip.core_model.accompany_board.profile.GetUserProfile

sealed class NotificationUiState {
    data object Initial : NotificationUiState()
    data object Loading : NotificationUiState()
    data class Success(val user: GetUserProfile) : NotificationUiState()
    data class Error(val message: String) : NotificationUiState()
}