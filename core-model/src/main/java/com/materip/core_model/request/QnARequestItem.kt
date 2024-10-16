package com.materip.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class QnARequestItem(
    val id: Int,
    val questions: String,
    val answers: String
)
