package com.materip.core_network.service.onboarding

import com.materip.core_model.request.SaveOnboardingRequestDto
import com.materip.core_model.response.DefaultResponseDto
import com.materip.core_model.response.IsOnboardingCompletedResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OnboardingService {
    /** 임의 response dto */
    @POST("/api/v1/profiles")
    suspend fun saveOnboardingResult(@Body requestDto: SaveOnboardingRequestDto): ApiResponse<DefaultResponseDto>

    /** 임의 response dto */
    @GET("/api/v1/profiles/exist")
    suspend fun isOnboardingCompleted(): ApiResponse<IsOnboardingCompletedResponseDto>
}