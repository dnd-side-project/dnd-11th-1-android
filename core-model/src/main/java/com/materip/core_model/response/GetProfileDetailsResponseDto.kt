package com.materip.core_model.response

data class GetProfileDetailsResponseDto(
    val birthYear: Int,
    val description: String,
    val foodPreferences: List<String>,
    val gender: String,
    val grade: String,
    val nickname: String,
    val profileImageUrl: String,
    val provider: String,
    val socialMediaUrl: String,
    val travelPreferences: List<String>,
    val travelStyles: List<String>,
    val userId: Int,
    val userImageUrls: List<String>
)