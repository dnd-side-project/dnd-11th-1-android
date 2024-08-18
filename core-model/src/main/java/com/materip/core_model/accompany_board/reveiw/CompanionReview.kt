package com.materip.core_model.accompany_board.reveiw

import kotlinx.serialization.Serializable

// TODO: 임시로 만든 데이터 모델
@Serializable
data class CompanionReview(
    val userId: Int,
    val boardReview: String,
    val boardRating: Float,
    val boardReviewDate: String,
    val boardReviewImage: String,
    val boardReviewLike: Int,
    val boardReviewDislike: Int,
    val boardReviewComment: Int,
) {
    fun hasContent(): Boolean {
        return boardReview.isNotEmpty()
    }
}
