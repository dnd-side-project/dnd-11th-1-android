package com.materip.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class QnARequestDto(
    val qnas: List<QnARequestItem>
)
