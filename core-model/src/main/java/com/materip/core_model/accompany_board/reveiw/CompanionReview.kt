package com.materip.core_model.accompany_board.reveiw

import kotlinx.serialization.Serializable

// TODO: 임시로 만든 동행 후기
@Serializable
data class CompanionReview(
    val boardId: Int,
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
