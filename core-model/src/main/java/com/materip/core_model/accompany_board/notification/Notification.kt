package com.materip.core_model.accompany_board.notification

import com.materip.core_model.accompany_board.request.CompanionRequest
import com.materip.core_model.accompany_board.reveiw.CompanionReview
import com.materip.core_model.response.SaveOnboardingResponseDto
import kotlinx.serialization.Serializable
import java.time.LocalDate

// TODO: 임시로 만든 데이터 모델, CompanionReview도 임시
@Serializable
data class Notification(
    val isSentByUser: SaveOnboardingResponseDto, // 요청을 보낸 유저를 뜻함
    val boardRequest: CompanionRequest?, //  요청을 보낸 유저의 동행신청
    val boardReview: CompanionReview?, //  요청을 보낸 유저의 동행리뷰
)
