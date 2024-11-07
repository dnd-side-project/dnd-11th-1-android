package com.materip.core_model.accompany_board.profile

import com.materip.core_model.ui_model.Gender
import kotlinx.serialization.Serializable

// 유저 프로필 조회 GET /api/v1/profiles/{userId}
@Serializable
data class GetUserProfile(
    val userId: Int,
    val nickname: String,
    val provider: String,
    val profileImageUrl: String?,
    val description: String?,
    val gender: Gender,
    val birthYear: Int,
    val socialMediaUrl: String?,
    val grade: String?,
    val travelPreferences: List<String>,
    val travelStyles: List<String>,
    val foodPreferences: List<String>,
    val userImageUrls: List<String>
) {
    fun getTags(): List<String> {
        return travelStyles.plus(travelPreferences).plus(foodPreferences)
    }
}