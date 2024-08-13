package com.materip.core_model.accompany_board

import kotlinx.serialization.Serializable

// 동행글 목록 조회 Data
@Serializable
data class Data(
    val boardId: Int,
    val endDate: String,
    val nickname: String,
    val region: String,
    val startDate: String,
    val title: String
)