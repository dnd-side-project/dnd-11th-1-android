package com.materip.core_model.request

data class UpdateProfileRequestDto(
    val birthYear: Int,
    val description: String,
    val foodPreferences: List<String>,
    val gender: String,
    val nickname: String,
    val profileImageUrl: String,
    val socialMediaLink: String,
    val travelPreferences: List<String>,
    val travelStyles: List<String>
)