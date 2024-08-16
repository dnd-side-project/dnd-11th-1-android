package com.materip.core_model.accompany_board.request

import kotlinx.serialization.Serializable

@Serializable
data class CompanionRequest(
    val boardId: Int,
    val introduce: String,
    val chatLink: String
)