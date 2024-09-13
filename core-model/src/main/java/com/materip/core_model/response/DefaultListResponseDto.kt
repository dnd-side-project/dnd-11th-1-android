package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class DefaultListResponseDto<T>(
    val responses: List<T>,
    val totalCount: Int
)