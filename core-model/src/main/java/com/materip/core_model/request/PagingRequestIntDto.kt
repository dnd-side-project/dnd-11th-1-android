package com.materip.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class PagingRequestIntDto(
    val cursor: Int?,
    val size: Int = 8
)
