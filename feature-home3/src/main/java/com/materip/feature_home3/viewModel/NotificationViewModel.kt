package com.materip.feature_home3.viewModel

import androidx.lifecycle.ViewModel
import com.materip.core_repository.repository.home_repository.BoardRepository
import com.materip.feature_home3.state.NotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val boardRepository: BoardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NotificationUiState>(NotificationUiState.Initial)
    val uiState: StateFlow<NotificationUiState> = _uiState
}