package com.materip.core_model.accompany_board.notification

import com.materip.core_model.accompany_board.request.CompanionRequest
import com.materip.core_model.accompany_board.reveiw.CompanionReview
import kotlinx.serialization.Serializable
import java.time.LocalDate

// TODO: 임시로 만든 데이터 모델, CompanionReview도 임시
@Serializable
data class Notification(
    val userId: Int,
    val boardRequest: CompanionRequest?,
    val boardReview: CompanionReview?,
) {
    fun getFormattedMessage(): String {
        val messages = mutableListOf<String>()

        boardRequest?.hasContent()?.let {
            messages.add("${userId}로부터 동행 신청서가 도착했습니다!")
        }

        boardReview?.hasContent()?.let {
            messages.add("${userId}로부터 동행 후기가 도착했습니다!")
        }

        return messages.joinToString(" ")
    }
}
