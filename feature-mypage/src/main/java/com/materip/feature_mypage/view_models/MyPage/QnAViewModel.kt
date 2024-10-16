package com.materip.feature_mypage.view_models.MyPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_common.Result
import com.materip.core_common.asResult
import com.materip.core_model.request.DefaultIdsRequestDto
import com.materip.core_model.request.PagingRequestIntDto
import com.materip.core_model.request.QnARequestDto
import com.materip.core_model.request.QnARequestItem
import com.materip.core_repository.repository.qna_repository.QnARepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QnAViewModel @Inject constructor(
    private val qnARepository: QnARepository
): ViewModel() {

    private val flag = MutableStateFlow<Boolean>(false)
    private val invalidTokenError = MutableStateFlow<Boolean>(false)
    private val notFoundTokenError = MutableStateFlow<Boolean>(false)
    private val generalError = MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))
    val errorState: StateFlow<ErrorState> = combine(invalidTokenError, notFoundTokenError, generalError){ invalidToken, notFoundToken, general ->
        ErrorState.AuthError(
            invalidToken,
            notFoundToken,
            general
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorState.Loading
    )

    val uiState = combine(errorState, flag){errState, flag ->
        if (errState is ErrorState.AuthError && errState.isInvalid()) throw Exception("")
        val requestDto = PagingRequestIntDto(cursor = null, size = 10)
        val result = qnARepository.getQnA(requestDto)
        Log.d("TAG TEST", "result : ${result}")
        if (result.error != null){
            when(result.error!!.status){
                401 -> invalidTokenError.update{true}
                404 -> notFoundTokenError.update{true}
                else -> generalError.update{Pair(true, result.error!!.message)}
            }
            throw Exception("Error")
        }
        result.data
    }.asResult().map{ result ->
        when(result){
            Result.Loading -> QnAUiState.Loading
            is Result.Error -> QnAUiState.Error
            is Result.Success -> {
                val data = result.data!!.data.map{QnARequestItem(id = it.id, questions = it.question, answers = it.answer)}
                QnAUiState.Success(data)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = QnAUiState.Loading
    )

    // qna 등록
    fun postQnA(quizs: List<QnARequestItem>){
        viewModelScope.launch{
            val qnas = quizs.map{ quiz ->
                QnARequestItem(
                    id = quiz.id,
                    questions = quiz.questions,
                    answers = quiz.answers
                )
            }
            val result = qnARepository.postQnA(QnARequestDto(qnas))
            if (result.error != null){
                when(result.error!!.status){
                    401 -> invalidTokenError.update{true}
                    404 -> notFoundTokenError.update{true}
                    else -> generalError.update{Pair(true, result.error!!.message)}
                }
                return@launch
            }
            flag.update{!flag.value}
        }
    }

    //qna 삭제
    fun deleteQna(ids: List<Int?>){
        viewModelScope.launch{
            val result = qnARepository.deleteQnA(DefaultIdsRequestDto(ids.filterNotNull()))
            if (result.error != null){
                when(result.error!!.status){
                    401 -> invalidTokenError.update{true}
                    404 -> notFoundTokenError.update{true}
                    else -> generalError.update{Pair(true, result.error!!.message)}
                }
                return@launch
            }
            flag.update{!flag.value}
        }
    }
}

sealed interface QnAUiState{
    data object Loading: QnAUiState
    data object Error: QnAUiState
    data class Success(
        val data: List<QnARequestItem>
    ): QnAUiState
}