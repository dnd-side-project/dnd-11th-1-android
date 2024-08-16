package com.materip.core_model.accompany_board

import kotlinx.serialization.Serializable

// 동행글 목록 조회 BoardResponseDto
@Serializable
data class BoardListResponse(
    val hasNext: Boolean, // 마지막 페이지인지 확인 여부
    val data: List<BoardItem>, // 실질 페이징 데이터
)