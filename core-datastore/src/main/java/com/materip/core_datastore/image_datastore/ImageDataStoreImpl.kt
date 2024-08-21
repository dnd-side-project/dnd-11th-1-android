package com.materip.core_datastore.image_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.mapper.transformMultipartBodyTypedImage
import com.materip.core_model.ui_model.DefaultImageDto
import com.materip.core_network.service.image.ImageService
import com.skydoves.sandwich.retrofit.apiMessage
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

class ImageDataStoreImpl @Inject constructor(
    private val imageService: ImageService
): ImageDataStore{
    override suspend fun postImage(requestDto: File): ResultResponse<DefaultImageDto> {
        val result = ResultResponse<DefaultImageDto>()
        imageService.postImage(requestDto.transformMultipartBodyTypedImage()).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun deleteImage(requestDto: DefaultImageDto): ResultResponse<Nothing> {
        val result = ResultResponse<Nothing>()
        imageService.deleteImage(requestDto).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
}