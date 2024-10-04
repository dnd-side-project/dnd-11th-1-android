package com.materip.core_model.accompany_board.mine

import kotlinx.serialization.Serializable

@Serializable
data class AccompanyBoardList(
    val cursor: String,
    val `data`: List<AccompanyBoardListItem>,
    val hasNext: Boolean
)