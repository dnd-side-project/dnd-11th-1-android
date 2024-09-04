package com.materip.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class AccompanyResponseInfo(
    val boardId: Int,
    val userId: Int,
    val introduce: String,
    val chatLink: String
)
