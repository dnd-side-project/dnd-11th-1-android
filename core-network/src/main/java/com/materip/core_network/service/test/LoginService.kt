package com.materip.core_network.service.test

import com.materip.core_model.request.LoginRequestDto
import com.materip.core_model.response.LoginResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginService {
    @GET("/api/v1/auth/sign-in")
    suspend fun loginKakao(@Query("loginRequest") loginRequestDto: LoginRequestDto): ApiResponse<LoginResponseDto>
}