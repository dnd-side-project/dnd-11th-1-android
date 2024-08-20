package com.materip.feature_home3.state

import com.materip.core_model.accompany_board.id.ProfileThumbnail
import com.materip.core_model.accompany_board.notification.Notification

sealed class NotificationUiState {
    data object Initial : NotificationUiState()
    data object Loading : NotificationUiState()
    // TODO: 알림에 대한 데이터 모델이 정의 되면 Success에 데이터를 넣어주기
    data class Success(
        val notifications: List<Notification>,
        val profileThumbnail: ProfileThumbnail
    ) : NotificationUiState()
    data class Error(val message: String) : NotificationUiState()
}