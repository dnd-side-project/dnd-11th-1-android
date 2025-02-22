package com.materip.core_datastore.com.materip.core_datastore.onboarding_datastore

import com.materip.core_common.ResponseError
import com.materip.core_common.ResultResponse
import com.materip.core_model.request.SaveOnboardingRequestDto
import com.materip.core_model.response.SaveOnboardingResponseDto
import com.materip.core_network.service.onboarding.OnboardingService
import com.skydoves.sandwich.retrofit.apiMessage
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class OnboardingDataStoreImpl @Inject constructor(
    private val onboardingService: OnboardingService
): OnboardingDataStore{
    override suspend fun saveOnboardingResult(requestDto: SaveOnboardingRequestDto): ResultResponse<SaveOnboardingResponseDto> {
        val result = ResultResponse<SaveOnboardingResponseDto>()
        onboardingService.saveOnboardingResult(requestDto).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }
        return result
    }
    override suspend fun isOnboardingCompleted(): ResultResponse<Boolean> {
        val result = ResultResponse<Boolean>()
        onboardingService.isOnboardingCompleted().suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }
        return result
    }
}