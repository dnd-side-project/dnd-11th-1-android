package com.materip.feature_home3.ui.component

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun formatDateStringToDisplay(dateTimeString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)

    return dateTime.format(outputFormatter)
}