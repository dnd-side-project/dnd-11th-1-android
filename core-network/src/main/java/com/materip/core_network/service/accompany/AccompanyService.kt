package com.materip.core_network.service.accompany

import com.materip.core_model.accompany_board.all.BoardItem
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.request.PagingRequestIntDto
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_model.response.BoardItemWithRequestId
import com.materip.core_model.response.BoardItemWithReviewId
import com.materip.core_model.response.DefaultPagingResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccompanyService {
    @GET("/api/v1/accompany/requests/{id}")
    suspend fun getAccompanyApplication(
        @Path("id") id: Int
    ): ApiResponse<AccompanyApplicationResponseDto>

    @POST("/api/v1/accompany/requests/{id}")
    suspend fun postCancel(
        @Path("id") id: Int
    ): ApiResponse<Any>
    @POST("/api/v1/accompany/requests/sended")
    suspend fun postAccompanySend(
        @Body requestDto: PagingRequestDto
    ): ApiResponse<DefaultPagingResponseDto<BoardItemWithRequestId>>

    @POST("/api/v1/accompany/requests/received")
    suspend fun postAccompanyReceived(
        @Body requestDto: PagingRequestDto
    ): ApiResponse<DefaultPagingResponseDto<AccompanyReceivedItem>>

    @POST("/api/v1/accompany/boards/records")
    suspend fun postAccompanyRecords(
        @Body requestDto: PagingRequestDto
    ): ApiResponse<DefaultPagingResponseDto<BoardItemWithReviewId>>

    @POST("/api/v1/accompany/boards/mine")
    suspend fun getAccompanyMyPost(
        @Body requestDto: PagingRequestDto
    ): ApiResponse<DefaultPagingResponseDto<BoardItem>>

    @POST("/api/v1/accompany/requests/decline/{id}")
    suspend fun postReject(
        @Path("id") id: Int
    ): ApiResponse<Any>

    @POST("/api/v1/accompany/requests/approve/{id}")
    suspend fun postAccept(
        @Path("id") id: Int
    ): ApiResponse<Any>

}