package com.materip.core_model.accompany_board.id

import kotlinx.serialization.Serializable

// 동행글 상세 조회 ProfileThumbnail
@Serializable
data class ProfileThumbnail(
    val userId: Int,
    val nickname: String,
    val profileImageUrl: String,
    val birthYear: Int,
    val gender: String,
)