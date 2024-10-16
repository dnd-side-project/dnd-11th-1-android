package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class QnAResponseDto(
    val cursor: Int,
    val id: Int,
    val question: String,
    val answer: String
)
