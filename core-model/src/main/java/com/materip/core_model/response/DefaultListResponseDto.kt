package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class DefaultListResponseDto<T>(
    var responses: List<T>,
    val totalCount: Int
)