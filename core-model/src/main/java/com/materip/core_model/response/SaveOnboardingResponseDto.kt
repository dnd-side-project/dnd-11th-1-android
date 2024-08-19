package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class SaveOnboardingResponseDto(
    val userId: Int
)