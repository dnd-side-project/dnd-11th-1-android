package com.materip.core_model.accompany_board.reveiw

import com.materip.core_model.ui_model.CompanionType
import com.materip.core_model.ui_model.PersonalityType
import com.materip.core_model.ui_model.Region
import com.materip.core_model.ui_model.TravelPreferenceForReview
import com.materip.core_model.ui_model.TravelStyle
import kotlinx.serialization.Serializable

// 리뷰 상세 조회 /api/v1/reviews/{id}
@Serializable
data class ReviewResponse(
    val writerNickname: String,
    val region: Region,
    val startDate: String,
    val endDate: String,
    val companionType: CompanionType,
    val personalityType: List<PersonalityType>,
    val travelPreference: List<TravelPreferenceForReview>,
    val travelStyle: List<TravelStyle>,
    val detailContent: String,
    val reviewImageUrls: List<String>
)
