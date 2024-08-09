package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class IsOnboardingCompletedResponseDto(
    val isCompleted: Boolean
)
