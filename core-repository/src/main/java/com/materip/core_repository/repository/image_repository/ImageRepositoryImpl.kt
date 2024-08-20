package com.materip.core_repository.repository.image_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.image_datastore.ImageDataStore
import com.materip.core_model.ui_model.DefaultImageDto
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageDataStore: ImageDataStore
): ImageRepository{
    override suspend fun postImage(file: File): ResultResponse<DefaultImageDto> {
        return imageDataStore.postImage(file)
    }

    override suspend fun deleteImage(requestDto: DefaultImageDto): ResultResponse<Nothing> {
        return imageDataStore.deleteImage(requestDto)
    }
}