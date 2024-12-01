package com.materip.feature_mypage.view_models.MyPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_common.Result
import com.materip.core_common.asResult
import com.materip.core_model.request.QnARequestDto
import com.materip.core_model.request.QnAItemDto
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
        val result = qnARepository.getQnA()
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
            is Result.Success -> QnAUiState.Success(result.data!!.qnas)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = QnAUiState.Loading
    )

    // qna 등록
    fun postQnA(quizs: List<QnAItemDto>){
        viewModelScope.launch{
            val qnas = quizs.map{ quiz ->
                QnAItemDto(
                    id = quiz.id,
                    question = quiz.question,
                    answer = quiz.answer
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
    fun deleteQna(qnas: List<QnAItemDto>){
        viewModelScope.launch{
            val deleteIds = qnas.map{it.id}.filterNotNull().toTypedArray()
            if (deleteIds.isNotEmpty()){
                val result = qnARepository.deleteQnA(deleteIds)
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
}

sealed interface QnAUiState{
    data object Loading: QnAUiState
    data object Error: QnAUiState
    data class Success(
        val data: List<QnAItemDto>
    ): QnAUiState
}