package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class DefaultListResponseDto<T>(
    val result: List<T>,
    val totalCount: Int
)