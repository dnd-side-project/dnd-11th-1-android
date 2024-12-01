package com.materip.core_network.service.qna

import com.materip.core_model.request.QnARequestDto
import com.materip.core_model.response.GetQnAResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface QnA100Service {
    @POST("/api/v1/qna100s/create-update")
    suspend fun postQnA(
        @Body requestDto: QnARequestDto
    ): ApiResponse<Any>
    @GET("/api/v1/qna100s/all")
    suspend fun getQnA(): ApiResponse<GetQnAResponseDto>
    @DELETE("/api/v1/qna100s")
    suspend fun deleteQnA(
        @Query("ids") ids: Array<Int>
    ): ApiResponse<Any>
}