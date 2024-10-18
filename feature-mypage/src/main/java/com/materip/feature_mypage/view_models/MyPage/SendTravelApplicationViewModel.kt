package com.materip.feature_mypage.view_models.MyPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.materip.core_common.ErrorState
import com.materip.core_model.response.BoardItemWithRequestId
import com.materip.core_repository.repository.accompany_repository.AccompanyRepository
import com.materip.feature_mypage.pager.SendApplicationPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SendTravelApplicationViewModel @Inject constructor(
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

    private val _uiState = MutableStateFlow<SendTravelApplicationUiState>(SendTravelApplicationUiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    fun applicationPagingSource(): Flow<PagingData<BoardItemWithRequestId>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            _uiState.update{SendTravelApplicationUiState.Success}
            getSendApplication()
        },
    ).flow.cachedIn(viewModelScope)

    private fun getSendApplication() = SendApplicationPagingSource(accompanyRepository = accompanyRepository)

}

sealed interface SendTravelApplicationUiState {
    data object Loading: SendTravelApplicationUiState
    data object Error: SendTravelApplicationUiState
    data object Success: SendTravelApplicationUiState
}