package com.materip.core_model.request

import com.materip.core_model.accompany_board.all.BoardItem
import com.materip.core_model.response.GetProfileDetailsResponseDto
import kotlinx.serialization.Serializable

@Serializable
data class AccompanyApplicationResponseDto(
    val boardThumbnail: BoardItem,
    val profileInfo: GetProfileDetailsResponseDto,
    val requestInfo: AccompanyResponseInfo
)
