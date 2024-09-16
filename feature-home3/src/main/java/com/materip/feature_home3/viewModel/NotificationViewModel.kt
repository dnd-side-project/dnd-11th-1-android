package com.materip.feature_home3.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_repository.repository.home_repository.BoardRepository
import com.materip.feature_home3.state.NotificationUiState
import com.materip.feature_home3.intent.NotificationIntent
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

    fun handleIntent(intent: NotificationIntent) {
        when (intent) {
            is NotificationIntent.FetchNotifications -> fetchNotifications()
        }
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            _uiState.value = NotificationUiState.Loading
            try {
                val userResult = boardRepository.getUserProfile()
                if (userResult.data != null) {
                    _uiState.value = NotificationUiState.Success(userResult.data!!)
                } else {
                    _uiState.value =
                        NotificationUiState.Error("알림 내역 로드 실패")
                }
            } catch (e: Exception) {
                _uiState.value =
                    NotificationUiState.Error(e.message ?: "알림 내역 로드 실패")
            }
        }
    }
}