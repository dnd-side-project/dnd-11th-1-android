package com.materip.core_model.request

data class LoginRequestDto(
    val provider: String,
    val accessToken: String
)
