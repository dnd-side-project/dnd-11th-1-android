package com.materip.core_common

@Serialiable
data class ResponseError(
    val errCode: Int,
    val errMsg: String
)
