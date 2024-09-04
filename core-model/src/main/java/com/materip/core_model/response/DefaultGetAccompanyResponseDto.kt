package com.materip.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class DefaultGetAccompanyResponseDto<T> (
    val hasNext: Boolean,
    val data: List<T>,
    val cursor: String
)