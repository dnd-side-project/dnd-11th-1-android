package com.materip.core_network.service

import com.materip.core_model.accompany_board.BoardRequestDto
import com.materip.core_model.accompany_board.BoardResponseDto
import com.materip.core_model.accompany_board.id.BoardIdDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardService {
    // 동행글 목록 조회
    @GET("/api/v1/accompany/boards")
    suspend fun getBoard(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 1
    ): ApiResponse<BoardResponseDto>

    // 동행글 생성
    @POST("/api/v1/accompany/boards")
    suspend fun postBoard(@Body board: BoardRequestDto): ApiResponse<BoardIdDto>

    // 동행글 상세 조회
    @GET("/api/v1/accompany/boards/{id}")
    suspend fun getBoardDetail(@Path("id") id: Int): ApiResponse<GetBoardDetailDto>
}
