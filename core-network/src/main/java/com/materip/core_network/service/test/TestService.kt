package com.materip.core_network.service.test

import com.materip.core_model.response.TestResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.POST


interface TestService {
    @GET("/test")
    suspend fun getTest(): ApiResponse<Any>

    @POST("/test")
    suspend fun postTest(): ApiResponse<TestResponseDto>
}