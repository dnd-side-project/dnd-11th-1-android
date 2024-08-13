package com.materip.core_network.service

import com.materip.core_model.BoardRequestDto
import com.materip.core_model.BoardResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BoardService {
    @GET("/api/v1/accompany/boards")
    suspend fun getBoard(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 1
    ): ApiResponse<BoardResponseDto>

    @POST("/api/v1/accompany/boards")
    suspend fun postBoard(@Body board: BoardRequestDto): ApiResponse<BoardRequestDto>
}
