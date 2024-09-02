package com.materip.core_model.accompany_board.profile

import kotlinx.serialization.Serializable

// 유저 프로필 조회 GET /api/v1/profiles
@Serializable
data class GetUserProfile(
    val userId: Int,
    val nickname: String,
    val birthYear: Int,
    val description: String,
    val foodPreferences: List<String>,
    val gender: String,
    val grade: String,
    val profileImageUrl: String,
    val provider: String,
    val socialMediaUrl: String,
    val travelPreferences: List<String>,
    val travelStyles: List<String>,
)