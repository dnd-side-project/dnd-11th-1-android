package com.materip.feature_home3.state

import com.materip.core_common.ResponseError

sealed class ImageUploadState {
    data object Idle : ImageUploadState()
    data object Loading : ImageUploadState()
    data class Success(val imagePath: String) : ImageUploadState()
    data class Error(val error: String) : ImageUploadState()
    data class Progress(val progress: Int) : ImageUploadState()
}