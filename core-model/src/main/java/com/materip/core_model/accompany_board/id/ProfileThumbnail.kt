package com.materip.core_model.accompany_board.id

import com.materip.core_model.ui_model.Gender
import kotlinx.serialization.Serializable

// 동행글 상세 조회 /api/v1/accompany/boards/{id}
@Serializable
data class ProfileThumbnail(
    val userId: Int,
    val nickname: String,
    val profileImageUrl: String,
    val birthYear: Int,
    val gender: Gender,
)