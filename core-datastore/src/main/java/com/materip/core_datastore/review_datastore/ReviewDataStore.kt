package com.materip.core_datastore.review_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.PostReviewRequestDto

interface ReviewDataStore {
    suspend fun postReview(requestDto: PostReviewRequestDto): ResultResponse<Any>
}