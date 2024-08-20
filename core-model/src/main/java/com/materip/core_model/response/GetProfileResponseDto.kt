package com.materip.core_model.response

data class GetProfileResponseDto(
    val userId: Int,
    val nickname: String,
    val provider: String,
    val profileImageUrl: String,
    val description: String?,
    val gender: String,
    val birthYear: Int,
    val socialMediaUrl: String?,
    val grade: String?,
    val travelPreferences: List<String>,
    val travelStyles: List<String>,
    val foodPreferences: List<String>
)