package com.materip.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class QnAItemDto(
    val id: Int?,
    var question: String,
    var answer: String
)
