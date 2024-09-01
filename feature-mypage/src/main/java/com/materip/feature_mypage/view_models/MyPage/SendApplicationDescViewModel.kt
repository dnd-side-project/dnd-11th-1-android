package com.materip.feature_mypage.view_models.MyPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_common.Result
import com.materip.core_common.asResult
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_repository.repository.accompany_repository.AccompanyRepository
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
class SendApplicationDescViewModel @Inject constructor(
    private val accompanyRepository: AccompanyRepository
): ViewModel() {
    private val applicationId = MutableStateFlow<Int?>(null)
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

    val uiState: StateFlow<SendApplicationDescUiState> = combine(errorState, applicationId){errState, id ->
        if (errState is ErrorState.AuthError && errState.isInvalid()) {throw Exception("Error")}
        val result = accompanyRepository.getAccompanyApplication(id = id!!)
        if(result.error != null) {
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
            Result.Loading -> SendApplicationDescUiState.Loading
            is Result.Error -> SendApplicationDescUiState.Error
            is Result.Success -> SendApplicationDescUiState.Success(result.data!!)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SendApplicationDescUiState.Loading
    )

    fun setId(id: Int?){
        if (id == null) generalError.update{Pair(true, "해당 신청을 찾지 못했습니다.")}
        applicationId.update{ id }
    }

    fun cancelApplication(){
    }
}

sealed interface SendApplicationDescUiState{
    data object Loading: SendApplicationDescUiState
    data object Error: SendApplicationDescUiState
    data class Success(
        val data: AccompanyApplicationResponseDto
    ): SendApplicationDescUiState
}