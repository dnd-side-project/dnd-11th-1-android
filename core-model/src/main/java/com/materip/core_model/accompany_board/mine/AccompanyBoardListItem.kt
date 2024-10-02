package com.materip.core_model.accompany_board.mine

import kotlinx.serialization.Serializable

@Serializable
data class AccompanyBoardListItem(
    val boardId: Int,
    val endDate: String,
    val imageUrls: List<String>,
    val nickname: String,
    val region: String,
    val startDate: String,
    val title: String
)