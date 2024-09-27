package com.materip.core_model.accompany_board.create

import com.materip.core_model.ui_model.BoardStatus
import com.materip.core_model.ui_model.Category
import com.materip.core_model.ui_model.PreferredAge
import com.materip.core_model.ui_model.PreferredGender
import com.materip.core_model.ui_model.Region
import kotlinx.serialization.Serializable

// 동행글 생성 /api/v1/accompany/boards
@Serializable
data class BoardRequestDto(
    val title: String,
    val content: String,
    val region: String,
    val startDate: String,
    val endDate: String,
    val capacity: Int,
    val boardStatus: String,
    val categories: List<String>,
    val preferredAge: String,
    val preferredGender: String,
    val imageUrls: List<String>,
    val tagNames: List<String>
)