package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class TestResponseDto(
    val description: String,
    val type: String
)
