package com.materip.feature_home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_repository.repository.BoardRepository
import com.materip.feature_home.intent.NotificationIntent
import com.materip.feature_home.state.NotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val boardRepository: BoardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NotificationUiState>(NotificationUiState.Initial)
    val uiState: StateFlow<NotificationUiState> = _uiState

    fun processIntent(intent: NotificationIntent) {
        when (intent) {
            is NotificationIntent.LoadNotifications -> loadNotification()
        }
    }

    private fun loadNotification() {
        viewModelScope.launch {
            _uiState.value = NotificationUiState.Loading
            try {
                val notificationsResponse = boardRepository.getNotifications()
                val profileThumbnailResponse = boardRepository.getProfileThumbnail()

                val notifications = notificationsResponse.data
                val profileThumbnail = profileThumbnailResponse.data

                if (notifications != null && profileThumbnail != null) {
                    _uiState.value = NotificationUiState.Success(
                        notifications,
                        profileThumbnail
                    )
                } else {
                    _uiState.value = NotificationUiState.Error("Failed to load data")
                }
            } catch (e: Exception) {
                _uiState.value = NotificationUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}