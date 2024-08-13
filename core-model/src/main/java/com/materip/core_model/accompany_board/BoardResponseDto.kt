package com.materip.core_model.accompany_board

import kotlinx.serialization.Serializable

// 동행글 목록 조회 BoardResponseDto
@Serializable
data class BoardResponseDto(
    val `data`: List<Data>,
    val hasNext: Boolean
)