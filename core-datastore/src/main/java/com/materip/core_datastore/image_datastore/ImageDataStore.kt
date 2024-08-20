package com.materip.core_datastore.image_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.ui_model.DefaultImageDto
import java.io.File

interface ImageDataStore {
    suspend fun postImage(requestDto: File): ResultResponse<DefaultImageDto>
    suspend fun deleteImage(requestDto: DefaultImageDto): ResultResponse<Nothing>
}