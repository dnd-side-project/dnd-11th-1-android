package com.materip.core_model.accompany_board.notification

import com.materip.core_model.accompany_board.request.CompanionRequest
import com.materip.core_model.accompany_board.reveiw.ReviewResponse
import com.materip.core_model.response.SaveOnboardingResponseDto
import kotlinx.serialization.Serializable

// TODO: 임시로 만든 데이터 모델
@Serializable
data class Notification(
    val isSentByUser: SaveOnboardingResponseDto, // 요청을 보낸 유저를 뜻함
    val boardRequest: CompanionRequest?, //  요청을 보낸 유저의 동행신청
    val boardReview: ReviewResponse?, //  요청을 보낸 유저의 동행리뷰
)
