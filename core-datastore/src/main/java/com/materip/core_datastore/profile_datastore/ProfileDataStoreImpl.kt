package com.materip.core_datastore.profile_datastore

import com.materip.core_common.ResponseError
import com.materip.core_common.ResultResponse
import com.materip.core_model.request.UpdateMyImagesRequestDto
import com.materip.core_model.request.UpdateProfileRequestDto
import com.materip.core_model.response.GetProfileDetailsResponseDto
import com.materip.core_model.response.GetProfileResponseDto
import com.materip.core_network.service.profile.ProfileService
import com.skydoves.sandwich.retrofit.apiMessage
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ProfileDataStoreImpl @Inject constructor(
    private val profileService: ProfileService
): ProfileDataStore{
    override suspend fun getProfile(): ResultResponse<GetProfileResponseDto> {
        val result = ResultResponse<GetProfileResponseDto>()
        profileService.getProfile().suspendOnError{
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun updateProfile(requestDto: UpdateProfileRequestDto): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        profileService.updateProfile(requestDto).suspendOnError{
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun updateMyImages(requestDto: UpdateMyImagesRequestDto): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        profileService.updateMyImages(requestDto).suspendOnError{
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun getProfileDetails(): ResultResponse<GetProfileDetailsResponseDto> {
        val result = ResultResponse<GetProfileDetailsResponseDto>()
        profileService.getProfileDetails().suspendOnError{
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
}