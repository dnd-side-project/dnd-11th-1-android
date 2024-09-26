package com.materip.core_network.service.accompany

import com.materip.core_model.accompany_board.all.BoardItem
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_model.response.BoardItemWithRequestId
import com.materip.core_model.response.BoardItemWithReviewId
import com.materip.core_model.response.DefaultGetAccompanyResponseDto
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
    @GET("/api/v1/accompany/requests/sended")
    suspend fun getAccompanySend(
        @Body requestDto: PagingRequestDto
    ): ApiResponse<DefaultGetAccompanyResponseDto<BoardItemWithRequestId>>

    @GET("/api/v1/accompany/requests/received")
    suspend fun getAccompanyReceived(
        @Body requestDto: PagingRequestDto
    ): ApiResponse<DefaultGetAccompanyResponseDto<AccompanyReceivedItem>>

    @GET("/api/v1/accompany/boards/records")
    suspend fun getAccompanyRecords(
        @Body requestDto: PagingRequestDto
    ): ApiResponse<DefaultGetAccompanyResponseDto<BoardItemWithReviewId>>

    @POST("/api/v1/accompany/boards/mine")
    suspend fun getAccompanyMyPost(
        @Body requestDto: PagingRequestDto
    ): ApiResponse<DefaultGetAccompanyResponseDto<BoardItem>>

    @POST("/api/v1/accompany/requests/decline/{id}")
    suspend fun postReject(
        @Path("id") id: Int
    ): ApiResponse<Any>

    @POST("/api/v1/accompany/requests/approve/{id}")
    suspend fun postAccept(
        @Path("id") id: Int
    ): ApiResponse<Any>

}