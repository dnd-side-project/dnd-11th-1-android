package com.materip.core_model.accompany_board.mine

import kotlinx.serialization.Serializable

@Serializable
data class GetAccompanyBoard(
    val cursor: String?,
    val size: Int = 8
)