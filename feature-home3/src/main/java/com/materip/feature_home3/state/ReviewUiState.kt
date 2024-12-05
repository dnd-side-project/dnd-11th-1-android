package com.materip.feature_home3.state

import com.materip.core_model.response.DefaultListResponseDto
import com.materip.core_model.response.ReviewItem

sealed class ReviewUiState {
    data object Initial : ReviewUiState()
    data object Loading : ReviewUiState()
    data class Success(
        val reviews: DefaultListResponseDto<ReviewItem>
    ) : ReviewUiState()
    data class Error(val message: String) : ReviewUiState()
}
