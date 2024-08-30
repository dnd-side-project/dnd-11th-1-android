package com.materip.core_repository.repository.review_repository

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.PostReviewRequestDto
import com.materip.core_model.response.DefaultListResponseDto
import com.materip.core_model.response.GetReviewDescriptionResponseDto
import com.materip.core_model.response.GetReviewEvaluationsResponseDto
import com.materip.core_model.response.ReviewItem

interface ReviewRepository {
    suspend fun getReviews(): ResultResponse<DefaultListResponseDto<ReviewItem>>
    suspend fun postReview(requestDto: PostReviewRequestDto): ResultResponse<Any>
    suspend fun getReviewDescription(id: Int): ResultResponse<GetReviewDescriptionResponseDto>
    suspend fun getReviewEvaluation(): ResultResponse<GetReviewEvaluationsResponseDto>
}