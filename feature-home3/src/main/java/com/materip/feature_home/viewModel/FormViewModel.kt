package com.materip.feature_home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.accompany_board.request.CompanionRequest
import com.materip.core_repository.repository.BoardRepository
import com.materip.feature_home.intent.FormIntent
import com.materip.feature_home.state.FormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val boardRepository: BoardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FormUiState>(FormUiState.Initial)
    val uiState: StateFlow<FormUiState> = _uiState

    private val _introduce = MutableStateFlow("")
    private val _chatLink = MutableStateFlow("")

    private val _isButtonEnabled = MutableStateFlow(true)
    val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled

    fun onFormIntent(intent: FormIntent) {
        when (intent) {
            is FormIntent.UpdateIntroduce -> onIntroduceChange(intent.introduce)
            is FormIntent.UpdateChatLink -> onChatLinkChange(intent.chatLink)
            is FormIntent.SubmitCompanionRequest -> submitCompanionRequest(intent.boardId)
        }
    }

    private fun onIntroduceChange(newIntroduce: String) {
        _introduce.value = newIntroduce
    }

    private fun onChatLinkChange(newChatLink: String) {
        _chatLink.value = newChatLink
    }

    private fun submitCompanionRequest(boardId: Int) {
        viewModelScope.launch {
            _uiState.value = FormUiState.Loading
            val request = CompanionRequest(
                boardId = boardId,
                introduce = _introduce.value,
                chatLink = _chatLink.value
            )
            val result = boardRepository.postCompanionRequest(request)
            _uiState.value = if (result.data != null) {
                FormUiState.Success
            } else {
                FormUiState.Error(result.error?.message ?: "동행 신청에 실패했습니다.")
            }
        }
    }
}