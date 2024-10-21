package com.materip.core_model.response

import com.materip.core_model.request.QnAItemDto
import kotlinx.serialization.Serializable

@Serializable
data class GetQnAResponseDto(
    val qnas: List<QnAItemDto>
)
