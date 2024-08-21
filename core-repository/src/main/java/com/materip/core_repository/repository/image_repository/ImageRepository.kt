package com.materip.core_repository.repository.image_repository

import com.materip.core_common.ResultResponse
import com.materip.core_model.ui_model.DefaultImageDto
import java.io.File

interface ImageRepository {
    suspend fun postImage(file: File): ResultResponse<DefaultImageDto>
    suspend fun deleteImage(requestDto: DefaultImageDto): ResultResponse<Nothing>
}