package com.materip.core_model.accompany_board.create

import com.materip.core_model.ui_model.BoardStatus
import com.materip.core_model.ui_model.Category
import com.materip.core_model.ui_model.PreferredAge
import com.materip.core_model.ui_model.PreferredGender
import com.materip.core_model.ui_model.Region

data class BoardFormState(
    val title: String = "",
    val content: String = "",
    val preferredGender: PreferredGender = PreferredGender.ANY,
    val preferredAge: PreferredAge = PreferredAge.ANY,
    val tagNames: List<String> = emptyList(),
    val category: List<Category> = emptyList(),
    val region: Region? = null,
    val startDate: String = "",
    val endDate: String = "",
    val capacity: Int = 2,
    val imageUris: List<String> = emptyList(),
    val boardStatus: BoardStatus = BoardStatus.RECRUITING
)