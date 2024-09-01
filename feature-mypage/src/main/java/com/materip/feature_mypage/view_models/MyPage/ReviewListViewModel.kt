package com.materip.feature_mypage.view_models.MyPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_common.Result
import com.materip.core_common.asResult
import com.materip.core_model.response.DefaultListResponseDto
import com.materip.core_model.response.ReviewItem
import com.materip.core_repository.repository.review_repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReviewListViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
): ViewModel(){

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
    val uiState: StateFlow<ReviewListUiState> = errorState.map{
        if (it is ErrorState.AuthError && it.isInvalid()) throw Exception("Error")
        val result = reviewRepository.getReviews()
        if(result.error != null){
            when(result.error!!.status){
                401 -> invalidTokenError.update{true}
                404 -> notFoundTokenError.update{true}
                else -> generalError.update{Pair(true, result.error!!.message)}
            }
        }
        result.data!!
    }.asResult().map{result ->
        when(result){
            Result.Loading -> ReviewListUiState.Loading
            is Result.Error -> ReviewListUiState.Error
            is Result.Success -> ReviewListUiState.Success(result.data!!)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ReviewListUiState.Loading
    )
}

sealed interface ReviewListUiState{
    data object Loading: ReviewListUiState
    data object Error: ReviewListUiState
    data class Success(
        val reviews: DefaultListResponseDto<ReviewItem>
    ): ReviewListUiState
}