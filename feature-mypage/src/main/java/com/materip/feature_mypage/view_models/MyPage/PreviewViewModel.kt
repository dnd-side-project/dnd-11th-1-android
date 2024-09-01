package com.materip.feature_mypage.view_models.MyPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_common.ErrorState
import com.materip.core_common.Result
import com.materip.core_common.asResult
import com.materip.core_model.response.DefaultListResponseDto
import com.materip.core_model.response.EvaluationItem
import com.materip.core_model.response.GetReviewEvaluationsResponseDto
import com.materip.core_model.response.ReviewItem
import com.materip.core_repository.repository.review_repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
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

    private val getReviewEvaluationFlow = flow{
        val result = reviewRepository.getReviewEvaluation()
        if (result.error != null) {
            when(result.error!!.status){
                401 -> invalidTokenError.update{true}
                404 -> notFoundTokenError.update{true}
                else -> generalError.update{Pair(true, result.error!!.message)}
            }
            throw Exception("Error")
        }
        emit(result.data)
    }.asResult()
    private val getReviewsFlow = flow{
        val result = reviewRepository.getReviews()
        if (result.error != null) {
            when(result.error!!.status){
                401 -> invalidTokenError.update{true}
                404 -> notFoundTokenError.update{true}
                else -> generalError.update{Pair(true, result.error!!.message)}
            }
            throw Exception("Error")
        }
        emit(result.data!!)
    }.asResult()
    val uiState: StateFlow<PreviewUiState> = combine(errorState, getReviewEvaluationFlow, getReviewsFlow){errState, reviewEvaluations, reviews ->
        if (errState is ErrorState.AuthError && errState.isInvalid()) throw Exception("Error")
        Pair(reviewEvaluations, reviews)
    }.map{result ->
        val (reviewEvaluationResult, reviewsResult) = result
        if (reviewEvaluationResult is Result.Error || reviewsResult is Result.Error){
            PreviewUiState.Error
        } else if (reviewEvaluationResult == Result.Loading || reviewsResult == Result.Loading) {
            PreviewUiState.Loading
        } else {
            var reviews = (reviewsResult as Result.Success).data
            if(reviews.totalCount > 3){
                reviews = reviews.copy(
                    result = reviews.result.subList(0,3)
                )
            }
            PreviewUiState.Success(
                evaluations = (reviewEvaluationResult as Result.Success).data!!,
                reviews = reviews
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PreviewUiState.Loading
    )

}

sealed interface PreviewUiState{
    data object Loading: PreviewUiState
    data object Error: PreviewUiState
    data class Success(
        val evaluations: GetReviewEvaluationsResponseDto,
        val reviews: DefaultListResponseDto<ReviewItem>
    ): PreviewUiState
}