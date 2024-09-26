package com.materip.core_model.accompany_board.all

import kotlinx.serialization.Serializable

// 동행글 목록 조회 /api/v1/accompany/boards/all
@Serializable
data class BoardListResponse(
    val hasNext: Boolean,
    val data: List<BoardItem>,
    val cursor: String?
)