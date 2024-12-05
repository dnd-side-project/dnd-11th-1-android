package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class DefaultPagingResponseDto<T> (
    val hasNext: Boolean,
    val data: List<T>,
    val cursor: String
)