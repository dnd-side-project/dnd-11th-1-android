package com.materip.core_datastore.review_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.PostReviewRequestDto
import com.materip.core_model.response.DefaultListResponseDto
import com.materip.core_model.response.GetReviewDescriptionResponseDto
import com.materip.core_model.response.GetReviewEvaluationsResponseDto
import com.materip.core_model.response.ReviewItem
import com.materip.core_network.service.review.ReviewService
import com.skydoves.sandwich.retrofit.apiMessage
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ReviewDataStoreImpl @Inject constructor(
    private val reviewService: ReviewService
): ReviewDataStore{
    override suspend fun getReviews(): ResultResponse<DefaultListResponseDto<ReviewItem>> {
        val result = ResultResponse<DefaultListResponseDto<ReviewItem>>()
        reviewService.getReviews().suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun postReview(requestDto: PostReviewRequestDto): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        reviewService.postReview(requestDto).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun getReviewDescription(id: Int): ResultResponse<GetReviewDescriptionResponseDto> {
        val result = ResultResponse<GetReviewDescriptionResponseDto>()
        reviewService.getReviewDescription(id).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun getReviewEvaluation(): ResultResponse<GetReviewEvaluationsResponseDto> {
        val result = ResultResponse<GetReviewEvaluationsResponseDto>()
        reviewService.getReviewEvaluations().suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess {
            result.data = this.data
        }
        return result
    }

    override suspend fun getReviewEvaluationAll(): ResultResponse<GetReviewEvaluationsResponseDto> {
        val result = ResultResponse<GetReviewEvaluationsResponseDto>()
        reviewService.getReviewEvaluationAll().suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
}