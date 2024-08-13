package com.materip.core_model.accompany_board.id

import kotlinx.serialization.Serializable

// 동행글 상세 조회 ProfileInfo
@Serializable
data class ProfileInfo(
    val birthYear: Int,
    val foodPreferences: List<String>,
    val gender: String,
    val nickname: String,
    val provider: String,
    val travelPreferences: List<String>,
    val travelStyles: List<String>
)