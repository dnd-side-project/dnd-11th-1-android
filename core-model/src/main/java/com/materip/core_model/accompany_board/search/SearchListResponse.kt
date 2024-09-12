package com.materip.core_model.accompany_board.search

import com.materip.core_model.accompany_board.all.BoardItem
import kotlinx.serialization.Serializable

// 동행글 검색 /api/v1/accompany/boards/search
@Serializable
data class SearchListResponse(
    val hasNext: Boolean,
    val data: List<BoardItem>,
    val cursor: String
)
