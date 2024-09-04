package com.materip.core_model.response

import com.materip.core_model.ui_model.Region
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Serializable
data class BoardItemWithRequestId(
    val requestId: Int,
    val title: String,
    val region: Region,
    val startDate: String,
    val endDate: String,
    val nickname: String,
    val imageUrls: List<String>
){
    fun getDuration(): String{
        val start = LocalDate.parse(startDate.substring(0,10))
        val end = LocalDate.parse(endDate.substring(0,10))
        val duration = ChronoUnit.DAYS.between(start, end)
        return "${duration}박 ${duration + 1}일"
    }
}
