package com.materip.core_model.accompany_board.id

import kotlinx.serialization.Serializable

// 동행글 상세 조회 BoardInfo
@Serializable
data class BoardInfo(
    val boardId: Int,
    val capacity: Int,
    val category: String,
    val content: String,
    val endDate: String,
    val headCount: Int,
    val preferredAge: String,
    val preferredGender: String,
    val region: String,
    val startDate: String,
    val title: String
)