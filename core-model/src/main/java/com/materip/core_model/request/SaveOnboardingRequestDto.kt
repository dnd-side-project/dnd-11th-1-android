package com.materip.core_model.request

data class SaveOnboardingRequestDto(
    val birthYear: Int,
    val gender: String,
    val travelPreferences: List<String>,
    val travelStyles: List<String>,
    val foodPreferences: List<String>
)
