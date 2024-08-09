package com.materip.core_model

import kotlinx.serialization.Serializable

@Serializable
data class BoardResponseDto(
    val `data`: List<Data>,
    val hasNext: Boolean
)