package com.materip.core_common

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val status: Int,
    val code: String,
    val message: String
)