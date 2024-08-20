package com.materip.core_repository.repository.profile_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.profile_datastore.ProfileDataStore
import com.materip.core_model.request.UpdateMyImagesRequestDto
import com.materip.core_model.request.UpdateProfileRequestDto
import com.materip.core_model.response.GetProfileDetailsResponseDto
import com.materip.core_model.response.GetProfileResponseDto
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataStore: ProfileDataStore
): ProfileRepository{
    override suspend fun getProfile(): ResultResponse<GetProfileResponseDto> {
        return profileDataStore.getProfile()
    }

    override suspend fun updateProfile(requestDto: UpdateProfileRequestDto): ResultResponse<Any> {
        return profileDataStore.updateProfile(requestDto)
    }

    override suspend fun updateMyImages(requestDto: UpdateMyImagesRequestDto): ResultResponse<Any> {
        return profileDataStore.updateMyImages(requestDto)
    }

    override suspend fun getProfileDetails(): ResultResponse<GetProfileDetailsResponseDto> {
        return profileDataStore.getProfileDetails()
    }
}