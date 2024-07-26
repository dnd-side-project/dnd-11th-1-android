package com.materip.core_common

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val errCode: Int,
    val errMsg: String
)
