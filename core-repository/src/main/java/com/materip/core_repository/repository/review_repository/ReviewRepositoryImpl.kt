package com.materip.core_repository.repository.review_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.review_datastore.ReviewDataStore
import com.materip.core_model.request.PostReviewRequestDto
import com.materip.core_model.response.DefaultListResponseDto
import com.materip.core_model.response.GetReviewDescriptionResponseDto
import com.materip.core_model.response.GetReviewEvaluationsResponseDto
import com.materip.core_model.response.ReviewItem
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDataStore: ReviewDataStore
): ReviewRepository{
    override suspend fun getReviews(): ResultResponse<DefaultListResponseDto<ReviewItem>> {
        return reviewDataStore.getReviews()
    }

    override suspend fun postReview(requestDto: PostReviewRequestDto): ResultResponse<Any> {
        return reviewDataStore.postReview(requestDto)
    }

    override suspend fun getReviewDescription(id: Int): ResultResponse<GetReviewDescriptionResponseDto> {
        return getReviewDescription(id)
    }

    override suspend fun getReviewEvaluation(): ResultResponse<GetReviewEvaluationsResponseDto> {
        return getReviewEvaluation()
    }
}