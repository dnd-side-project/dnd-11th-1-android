package com.materip.feature_mypage.view_models.MyPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.materip.core_common.ErrorState
import com.materip.core_common.Result
import com.materip.core_common.asResult
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_repository.repository.accompany_repository.AccompanyRepository
import com.materip.feature_mypage.pager.ReceiveApplicationPagingSource
import com.materip.feature_mypage.pager.SendApplicationPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ReceiveTravelApplicationViewModel @Inject constructor(
    private val accompanyRepository: AccompanyRepository
): ViewModel() {

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

    val uiState: StateFlow<ReceiveTravelApplicationUiState> = errorState.map{
        if (it is ErrorState.AuthError && it.isInvalid()) {throw Exception("Error")}
    }.asResult().map{ result ->
        when(result){
            Result.Loading -> ReceiveTravelApplicationUiState.Loading
            is Result.Success -> ReceiveTravelApplicationUiState.Success
            is Result.Error -> ReceiveTravelApplicationUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ReceiveTravelApplicationUiState.Loading
    )

    fun applicationPagingSource(): Flow<PagingData<AccompanyReceivedItem>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {getSendApplication()},
    ).flow.cachedIn(viewModelScope)

    private fun getSendApplication() = ReceiveApplicationPagingSource(accompanyRepository = accompanyRepository)

}

sealed interface ReceiveTravelApplicationUiState {
    data object Loading: ReceiveTravelApplicationUiState
    data object Error: ReceiveTravelApplicationUiState
    data object Success: ReceiveTravelApplicationUiState
}