package com.materip.core_model.accompany_board

import com.materip.core_model.accompany_board.create.Region
import kotlinx.serialization.Serializable

// 동행글 목록 조회 Data
@Serializable
data class BoardItem(
    val boardId: Int,
    val title: String,
    val region: Region,
    val startDate: String,
    val endDate: String,
    val nickname: String,
    val imageUrls: List<String>
)