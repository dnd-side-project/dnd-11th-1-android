package com.materip.feature_home3.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.accompany_board.mine.GetAccompanyBoard
import com.materip.core_model.accompany_board.request.CompanionRequest
import com.materip.core_repository.repository.home_repository.BoardRepository
import com.materip.feature_home3.intent.FormIntent
import com.materip.feature_home3.state.FormUiState
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
    val introduce: StateFlow<String> = _introduce

    private val _chatLink = MutableStateFlow("")
    val chatLink: StateFlow<String> = _chatLink

    private var companionRequest: CompanionRequest? = null

    private val _showDialogState = MutableStateFlow(false)
    val showDialogState: StateFlow<Boolean> = _showDialogState

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled

    fun onFormIntent(intent: FormIntent) {
        when (intent) {
            is FormIntent.UpdateIntroduce -> onIntroduceChange(intent.introduce)
            is FormIntent.UpdateChatLink -> onChatLinkChange(intent.chatLink)
            is FormIntent.SubmitCompanionRequest -> submitCompanionRequest(intent.boardId)
            is FormIntent.ShowDialog -> showDialog()
            is FormIntent.DismissDialog -> dismissDialog()
            is FormIntent.CheckIfUserIsAuthor -> checkIfUserIsAuthor(intent.boardId)
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

            companionRequest = CompanionRequest(
                boardId = boardId,
                introduce = _introduce.value,
                chatLink = _chatLink.value
            )

            val result = boardRepository.postCompanionRequest(companionRequest!!)

            _uiState.value = if (result.data != null) {
                FormUiState.Success
            } else {
                FormUiState.Error(result.error?.message ?: "동행 신청에 실패했습니다.")
            }
        }
    }

    private fun showDialog() {
        _showDialogState.value = true
    }

    private fun dismissDialog() {
        _showDialogState.value = false
    }

    fun checkIfUserIsAuthor(boardId: Int) {
        viewModelScope.launch {
            _uiState.value = FormUiState.Loading

            val result = boardRepository.getMyBoardList(GetAccompanyBoard(cursor = null, size = 1000))
            val accompanyBoardList = result.data

            if (accompanyBoardList != null) {
                val myBoardList = accompanyBoardList.data
                val isAuthor = myBoardList.any { it.boardId == boardId }
                _isButtonEnabled.value = !isAuthor

                _uiState.value = FormUiState.Success
            } else {
                _isButtonEnabled.value = false
                _uiState.value = FormUiState.Error(result.error?.message ?: "작성자 확인에 실패했습니다.")
            }
        }
    }


}