package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetProfileDetailsResponseDto(
    val userId: Int,
    val nickname: String,
    val provider: String,
    val profileImageUrl: String?,
    val description: String?,
    val gender: String,
    val birthYear: Int,
    val socialMediaUrl: String?,
    val grade: String?,
    val travelPreferences: List<String>,
    val travelStyles: List<String>,
    val foodPreferences: List<String>,
    val userImageUrls: List<String>
)