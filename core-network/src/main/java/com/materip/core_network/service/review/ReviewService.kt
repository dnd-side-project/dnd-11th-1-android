package com.materip.core_network.service.review

import com.materip.core_model.request.PostReviewRequestDto
import com.materip.core_model.response.DefaultListResponseDto
import com.materip.core_model.response.GetReviewDescriptionResponseDto
import com.materip.core_model.response.GetReviewEvaluationsResponseDto
import com.materip.core_model.response.ReviewItem
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewService {
    @GET("/api/v1/reviews")
    suspend fun getReviews(): ApiResponse<DefaultListResponseDto<ReviewItem>>
    @POST("/api/v1/reviews")
    suspend fun postReview(
        @Body requestDto: PostReviewRequestDto
    ): ApiResponse<Any>
    @GET("/api/v1/reviews/{id}")
    suspend fun getReviewDescription(
        @Path("id")id: Int
    ): ApiResponse<GetReviewDescriptionResponseDto>
    @GET("/api/v1/reviews/evaluations")
    suspend fun getReviewEvaluations(): ApiResponse<GetReviewEvaluationsResponseDto>
    @GET("/api/v1/reviews/evaluations/all")
    suspend fun getReviewEvaluationAll(): ApiResponse<GetReviewEvaluationsResponseDto>
}