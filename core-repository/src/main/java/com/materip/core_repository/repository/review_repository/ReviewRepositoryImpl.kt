package com.materip.core_repository.repository.review_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.review_datastore.ReviewDataStore
import com.materip.core_model.request.PostReviewRequestDto
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDataStore: ReviewDataStore
): ReviewRepository{
    override suspend fun postReview(requestDto: PostReviewRequestDto): ResultResponse<Any> {
        return reviewDataStore.postReview(requestDto)
    }
}