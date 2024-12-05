package com.materip.core_network.service.image

import com.materip.core_model.ui_model.DefaultImageDto
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageService {
    @Multipart
    @POST("/api/v1/images")
    suspend fun postImage(@Part file: MultipartBody.Part): ApiResponse<DefaultImageDto>

    @DELETE("/api/v1/images")
    suspend fun deleteImage(@Body requestDto: DefaultImageDto): ApiResponse<Nothing>
}