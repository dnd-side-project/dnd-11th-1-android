package com.materip.core_model.accompany_board.search

import kotlinx.serialization.Serializable

// 동행글 검색 /api/v1/accompany/boards/search
@Serializable
data class QueryRequestDto(
    val keyword: String,
    val cursor: String?,
    val size: Int = 0
)
