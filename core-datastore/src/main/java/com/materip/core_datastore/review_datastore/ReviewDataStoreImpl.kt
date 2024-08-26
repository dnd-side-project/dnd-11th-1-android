package com.materip.core_datastore.review_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.PostReviewRequestDto
import com.materip.core_network.service.review.ReviewService
import com.skydoves.sandwich.retrofit.apiMessage
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ReviewDataStoreImpl @Inject constructor(
    private val reviewService: ReviewService
): ReviewDataStore{
    override suspend fun postReview(requestDto: PostReviewRequestDto): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        reviewService.postReview(requestDto).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
}