package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetReviewEvaluationsResponseDto (
    val evaluationResponse: List<EvaluationItem>,
    val evaluationCount: Int
)