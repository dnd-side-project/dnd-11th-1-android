package com.materip.core_model.response

import com.materip.core_model.ui_model.Gender

data class GetProfileResponseDto(
    val userId: Int,
    val nickname: String,
    val provider: String,
    val profileImageUrl: String,
    val description: String?,
    val gender: Gender,
    val birthYear: Int,
    val socialMediaUrl: String?,
    val grade: String?,
    val travelPreferences: List<String>,
    val travelStyles: List<String>,
    val foodPreferences: List<String>
)