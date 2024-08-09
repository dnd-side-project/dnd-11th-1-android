package com.materip.core_repository.repository.onboarding_repository

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.SaveOnboardingRequestDto
import com.materip.core_model.response.SaveOnboardingResponseDto

interface OnboardingRepository {
    suspend fun saveOnboardingResult(requestDto: SaveOnboardingRequestDto): ResultResponse<SaveOnboardingResponseDto>
    suspend fun isOnboardingCompleted(): ResultResponse<Boolean>
}