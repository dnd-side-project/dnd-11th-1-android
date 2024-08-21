package com.materip.core_repository.repository.profile_repository

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.UpdateMyImagesRequestDto
import com.materip.core_model.request.UpdateProfileRequestDto
import com.materip.core_model.response.GetProfileDetailsResponseDto
import com.materip.core_model.response.GetProfileResponseDto

interface ProfileRepository {
    suspend fun getProfile(): ResultResponse<GetProfileResponseDto>
    suspend fun updateProfile(requestDto: UpdateProfileRequestDto): ResultResponse<Any>
    suspend fun updateMyImages(requestDto: UpdateMyImagesRequestDto): ResultResponse<Any>
    suspend fun getProfileDetails(): ResultResponse<GetProfileDetailsResponseDto>
}