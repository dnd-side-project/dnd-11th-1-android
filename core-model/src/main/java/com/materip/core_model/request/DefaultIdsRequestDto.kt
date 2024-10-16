package com.materip.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class DefaultIdsRequestDto(
    val ids: List<Int>
)
