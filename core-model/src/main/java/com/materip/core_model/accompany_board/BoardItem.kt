package com.materip.core_model.accompany_board

import kotlinx.serialization.Serializable

// 동행글 목록 조회 Data
@Serializable
data class BoardItem(
    val boardId: Int,
    val title: String,
    val region: String,
    val startDate: String,
    val endDate: String,
    val nickname: String,
    val imageUrls: List<String>
)