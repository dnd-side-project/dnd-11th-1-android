package com.materip.core_network.service.accompany

import com.materip.core_model.accompany_board.BoardItem
import com.materip.core_model.accompany_board.BoardListResponse
import com.materip.core_model.accompany_board.Pageable
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_model.response.DefaultGetAccompanyResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccompanyService {
    @GET("/api/v1/accompany/requests/{id}")
    suspend fun getAccompanyApplication(
        @Path("id") id: Int
    ): ApiResponse<AccompanyApplicationResponseDto>

    @GET("/api/v1/accompany/requests/sended")
    suspend fun getAccompanySend(
        @Query("pageable") pageable: Pageable
    ): ApiResponse<DefaultGetAccompanyResponseDto<BoardItem>>

    @GET("/api/v1/accompany/requests/received")
    suspend fun getAccompanyReceived(
        @Query("pageable") pageable: Pageable
    ): ApiResponse<DefaultGetAccompanyResponseDto<AccompanyReceivedItem>>

    @GET("/api/v1/accompany/boards/records")
    suspend fun getAccompanyRecords(
        @Query("pageable") pageable: Pageable
    ): ApiResponse<DefaultGetAccompanyResponseDto<BoardItem>>
}