package com.materip.core_network.service.onboarding

import com.materip.core_model.request.SaveOnboardingRequestDto
import com.materip.core_model.response.SaveOnboardingResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OnboardingService {
    @POST("/api/v1/profiles")
    suspend fun saveOnboardingResult(@Body requestDto: SaveOnboardingRequestDto): ApiResponse<SaveOnboardingResponseDto>

    @GET("/api/v1/profiles/exist")
    suspend fun isOnboardingCompleted(): ApiResponse<Boolean>
}