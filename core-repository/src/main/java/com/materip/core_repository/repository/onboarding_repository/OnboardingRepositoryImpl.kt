package com.materip.core_repository.repository.onboarding_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.com.materip.core_datastore.onboarding_datastore.OnboardingDataStore
import com.materip.core_model.request.SaveOnboardingRequestDto
import com.materip.core_model.response.DefaultResponseDto
import com.materip.core_model.response.IsOnboardingCompletedResponseDto
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val onboardingDataStore: OnboardingDataStore
): OnboardingRepository{
    override suspend fun saveOnboardingResult(requestDto: SaveOnboardingRequestDto): ResultResponse<DefaultResponseDto> {
        return onboardingDataStore.saveOnboardingResult(requestDto)
    }

    override suspend fun isOnboardingCompleted(): ResultResponse<IsOnboardingCompletedResponseDto> {
        return onboardingDataStore.isOnboardingCompleted()
    }
}