package com.materip.core_model.request

data class LoginRequestDto(
    val provider: String = "KAKAO",
    val accessToken: String
)
