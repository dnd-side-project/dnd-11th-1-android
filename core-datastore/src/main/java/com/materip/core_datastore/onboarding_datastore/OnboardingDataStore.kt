package com.materip.core_datastore.com.materip.core_datastore.onboarding_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.SaveOnboardingRequestDto
import com.materip.core_model.response.SaveOnboardingResponseDto

interface OnboardingDataStore {
    suspend fun saveOnboardingResult(requestDto: SaveOnboardingRequestDto): ResultResponse<SaveOnboardingResponseDto>
    suspend fun isOnboardingCompleted(): ResultResponse<Boolean>
}