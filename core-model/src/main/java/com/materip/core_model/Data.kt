package com.materip.core_model

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val boardId: Int,
    val endDate: String,
    val nickname: String,
    val region: String,
    val startDate: String,
    val title: String
)