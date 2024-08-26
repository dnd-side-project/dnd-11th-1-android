package com.materip.core_network.service.review

import com.materip.core_model.request.PostReviewRequestDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ReviewService {
    @POST("/api/v1/reviews")
    suspend fun postReview(
        @Body requestDto: PostReviewRequestDto
    ): ApiResponse<Any>
}