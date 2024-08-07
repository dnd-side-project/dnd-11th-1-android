package com.materip.core_network.service.login

import com.materip.core_model.request.LoginRequestDto
import com.materip.core_model.response.LoginResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/api/v1/auth/sign-in")
    suspend fun loginKakao(@Body loginRequestDto: LoginRequestDto): ApiResponse<LoginResponseDto>
}