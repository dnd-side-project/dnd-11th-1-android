package com.materip.feature_home3.ui.component

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun calculateDuration(startDate: String, endDate: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val start = LocalDate.parse(startDate, formatter)
    val end = LocalDate.parse(endDate, formatter)
    val days = ChronoUnit.DAYS.between(start, end).toInt() + 1
    val nights = days - 1
    return "${nights}박 ${days}일"
}