package com.materip.core_model.accompany_board.create

import com.materip.core_model.ui_model.BoardStatus
import com.materip.core_model.ui_model.Category
import com.materip.core_model.ui_model.PreferredAge
import com.materip.core_model.ui_model.PreferredGender
import com.materip.core_model.ui_model.Region
import kotlinx.serialization.Serializable

// 동행글 생성 BoardRequestDto
@Serializable
data class BoardRequestDto(
    val title: String,
    val content: String,
    val region: Region,
    val startDate: String,
    val endDate: String,
    val capacity: Int,
    val boardStatus: BoardStatus,
    val categories: List<Category>,
    val preferredAge: PreferredAge,
    val preferredGender: PreferredGender,
    val imageUrls: List<String>,
    val tagNames: List<String>
)