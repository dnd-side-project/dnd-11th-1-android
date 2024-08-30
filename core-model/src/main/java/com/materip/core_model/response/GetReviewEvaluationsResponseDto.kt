package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetReviewEvaluationsResponseDto (
    val travelStyle: String,
    val travelPreference: String,
    val personalityType: String,
    val evaluationCount: Int
)