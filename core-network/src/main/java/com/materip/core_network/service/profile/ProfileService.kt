package com.materip.core_network.service.profile

import com.materip.core_model.request.UpdateMyImagesRequestDto
import com.materip.core_model.request.UpdateProfileRequestDto
import com.materip.core_model.response.GetProfileDetailsResponseDto
import com.materip.core_model.response.GetProfileResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ProfileService {
    @GET("/api/v1/profiles")
    suspend fun getProfile(): ApiResponse<GetProfileResponseDto>

    @PUT("/api/v1/profiles")
    suspend fun updateProfile(@Body requestDto: UpdateProfileRequestDto): ApiResponse<Any>

    @PUT("/api/v1/profiles/images")
    suspend fun updateMyImages(@Body requestDto: UpdateMyImagesRequestDto): ApiResponse<Any>

    @GET("/api/v1/profiles/details")
    suspend fun getProfileDetails(): ApiResponse<GetProfileDetailsResponseDto>
}