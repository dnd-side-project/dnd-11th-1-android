package com.materip.core_datastore.mapper

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun File.transformMultipartBodyTypedImage(): MultipartBody.Part{
    val imageRequestBody = this.asRequestBody("image/*".toMediaTypeOrNull())
    val imagePart = MultipartBody.Part.createFormData("file", this.name, imageRequestBody)
    return imagePart
}