package com.materip.core_model.accompany_board.id

import kotlinx.serialization.Serializable

// 동행글 상세 조회 /api/v1/accompany/boards/{id}
@Serializable
data class GetBoardDetailDto(
    val boardInfo: BoardInfo,
    val profileThumbnail: ProfileThumbnail
)