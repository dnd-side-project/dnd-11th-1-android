package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetProfileDetailsResponseDto(
    val userId: Int,
    val nickname: String,
    val provider: String,
    val gender: String,
    val birthYear: Int,
    val description: String?,
    val grade: String?,
    val profileImageUrl: String?,
    val socialMediaUrl: String?,
    val travelPreferences: List<String>,
    val travelStyles: List<String>,
    val userImageUrls: List<String>,
    val foodPreferences: List<String>
) {
    fun getAge(): String{
        return when(birthYear){
            in 0..9 -> "${birthYear}세"
            in 10..19 -> "10대"
            in 20..29 -> "20대"
            in 30..39 -> "30대"
            in 40..49 -> "40대"
            in 50..59 -> "50대"
            in 60..69 -> "60대"
            in 70..79 -> "70대"
            in 80..89 -> "80대"
            else -> "90대"
        }
    }
}