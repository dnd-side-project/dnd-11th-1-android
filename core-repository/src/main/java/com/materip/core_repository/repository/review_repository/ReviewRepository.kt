package com.materip.core_repository.repository.review_repository

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.PostReviewRequestDto

interface ReviewRepository {
    suspend fun postReview(requestDto: PostReviewRequestDto): ResultResponse<Any>
}