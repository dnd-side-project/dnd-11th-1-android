package com.materip.core_model.response

import com.materip.core_model.accompany_board.BoardItem
import kotlinx.serialization.Serializable

@Serializable
data class DefaultGetAccompanyResponseDto<T> (
    val hasNext: Boolean,
    val data: List<T>,
    val cursor: String
)