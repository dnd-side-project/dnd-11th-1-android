package com.materip.core_model.accompany_board.all

import com.materip.core_model.ui_model.Region
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.temporal.ChronoUnit

// 동행글 목록 조회 /api/v1/accompany/boards/all
@Serializable
data class BoardItem(
    val boardId: Int,
    val title: String,
    val region: Region,
    val startDate: String,
    val endDate: String,
    val nickname: String,
    val imageUrls: List<String>
) {
    fun getStartDateText(): String{
        return startDate.substring(0, 10)
    }
    fun getEndDateText(): String{
        return endDate.substring(0, 10)
    }
    fun getDuration(): String{
        val start = LocalDate.parse(startDate.substring(0,10))
        val end = LocalDate.parse(endDate.substring(0,10))
        val duration = ChronoUnit.DAYS.between(start, end)
        return "${duration}박 ${duration + 1}일"
    }
}