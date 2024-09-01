package com.materip.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class PagingRequestDto(
    val cursor: String?,
    val size: Int = 10
)
