package com.materip.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class QnARequestItem(
    val id: Int?,
    var questions: String,
    var answers: String
)
