package com.materip.core_model.accompany_board.id

import kotlinx.serialization.Serializable

// 동행글 상세 조회 ProfileInfo
@Serializable
data class ProfileInfo(
    val nickname: String,
    val profileImageUrl: String,
    val provider: String,
    val birthYear: Int,
    val gender: String,
    val travelPreferences: List<String>,
    val travelStyles: List<String>,
    val foodPreferences: List<String>
)