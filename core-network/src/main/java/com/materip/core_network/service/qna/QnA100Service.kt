package com.materip.core_network.service.qna

import com.materip.core_model.accompany_board.all.BoardListResponse
import com.materip.core_model.request.DefaultIdsRequestDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.request.PagingRequestIntDto
import com.materip.core_model.request.QnARequestDto
import com.materip.core_model.response.DefaultPagingResponseDto
import com.materip.core_model.response.QnAResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface QnA100Service {
    @POST("/api/v1/qna100s/create-update")
    suspend fun postQnA(
        @Body requestDto: QnARequestDto
    ): ApiResponse<Any>
    @POST("/api/v1/qna100s/all")
    suspend fun getQnA(
        @Body requestDto: PagingRequestIntDto
    ): ApiResponse<DefaultPagingResponseDto<QnAResponseDto>>
    @DELETE("/api/v1/qna100s")
    suspend fun deleteQnA(
        @Body requestDto: DefaultIdsRequestDto
    ): ApiResponse<Any>
}