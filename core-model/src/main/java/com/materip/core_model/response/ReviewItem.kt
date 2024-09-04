package com.materip.core_model.response

import com.materip.core_model.ui_model.Region
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Serializable
data class ReviewItem(
    val nickname: String,
    val profileImageUrl: String,
    val region: Region,
    val startDate: String,
    val endDate: String,
    val detailContent: String
){
    fun getDuration(): String{
        val start = LocalDate.parse(startDate.substring(0,10))
        val end = LocalDate.parse(endDate.substring(0,10))
        val duration = ChronoUnit.DAYS.between(start, end)
        return "${duration}박 ${duration + 1}일"
    }
}