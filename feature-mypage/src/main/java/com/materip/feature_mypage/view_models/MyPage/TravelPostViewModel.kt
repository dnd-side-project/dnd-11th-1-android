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
import com.materip.core_model.accompany_board.all.BoardItem
import com.materip.core_repository.repository.accompany_repository.AccompanyRepository
import com.materip.feature_mypage.pager.AccompanyMyPostPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TravelPostViewModel @Inject constructor(
    private val accompanyRepository: AccompanyRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<TravelPostUiState>(TravelPostUiState.Loading)
    val uiState get() = _uiState.asStateFlow()

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

    fun applicationPagingSource(): Flow<PagingData<BoardItem>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            _uiState.update{TravelPostUiState.Success}
            getAccompanyMyPost()
        },
    ).flow.cachedIn(viewModelScope)

    private fun getAccompanyMyPost() = AccompanyMyPostPagingSource(accompanyRepository = accompanyRepository)

}

sealed interface TravelPostUiState{
    data object Loading: TravelPostUiState
    data object Error: TravelPostUiState
    data object Success: TravelPostUiState
}