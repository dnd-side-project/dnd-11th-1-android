package com.materip.feature_mypage.view_models.MyPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_common.Result
import com.materip.core_common.asResult
import com.materip.core_model.accompany_board.id.GetBoardDetailDto
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_repository.repository.accompany_repository.AccompanyRepository
import com.materip.core_repository.repository.accompany_repository.AccompanyRepositoryImpl
import com.materip.core_repository.repository.home_repository.BoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReceivedApplicationViewModel @Inject constructor(
    private val accompanyRepository: AccompanyRepository
): ViewModel() {
    private val id = MutableStateFlow<Int?>(null)
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
    val uiState: StateFlow<ReceivedApplicationUiState> = combine(errorState, id){ errState, id ->
        if (errState is ErrorState.AuthError && errState.isInvalid()) throw Exception("Error")
        val result = accompanyRepository.getAccompanyApplication(id = id!!)
        if(result.error != null) {
            when(result.error!!.status){
                401 -> invalidTokenError.update{true}
                404 -> notFoundTokenError.update{true}
                else -> generalError.update{Pair(true, result.error!!.message)}
            }
            throw Exception("Error")
        }
        result.data!!
    }.asResult().map{ result ->
        when(result){
            Result.Loading -> ReceivedApplicationUiState.Loading
            is Result.Error -> ReceivedApplicationUiState.Error
            is Result.Success -> ReceivedApplicationUiState.Success(result.data)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ReceivedApplicationUiState.Loading
    )

    fun setId(newId: Int?){
        if (newId == null){
            generalError.update{Pair(true, "해당 글을 찾을 수 없습니다.")}
            return
        }
        id.update{newId}
    }
}

sealed interface ReceivedApplicationUiState{
    data object Loading: ReceivedApplicationUiState
    data object Error: ReceivedApplicationUiState
    data class Success(
        val data: AccompanyApplicationResponseDto
    ): ReceivedApplicationUiState
}