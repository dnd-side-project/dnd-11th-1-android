package com.materip.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class PostReviewRequestDto(
    val receiverId: Int,
    val accompanyBoardId: Int,
    val satisfactionLevel: String,
    val recommendationStatus: String,
    val companionType: String,
    val personalityType: List<String>,
    val travelPreference: List<String>,
    val travelStyle: List<String>,
    val detailContent: String,
    val reviewImageUrls: List<String>
)