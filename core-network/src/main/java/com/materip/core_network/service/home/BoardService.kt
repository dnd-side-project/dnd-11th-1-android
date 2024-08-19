package com.materip.core_network.service.home

import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.accompany_board.BoardListResponse
import com.materip.core_model.accompany_board.id.BoardIdDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto
import com.materip.core_model.accompany_board.request.CompanionRequest
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
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
    ): ApiResponse<BoardListResponse>

    // 동행글 생성
    @POST("/api/v1/accompany/boards")
    suspend fun postBoard(@Body board: BoardRequestDto): ApiResponse<BoardIdDto>

    // 동행글 상세 조회
    @GET("/api/v1/accompany/boards/{id}")
    suspend fun getBoardDetail(@Path("id") id: Int): ApiResponse<GetBoardDetailDto>

    // 동행 신청
    @POST("/api/v1/accompany/boards/request")
    suspend fun postCompanionRequest(@Body companionRequest: CompanionRequest): ApiResponse<Unit>

    // 동행글 삭제
    @DELETE("/api/v1/accompany/boards/{id}")
    suspend fun deleteBoard(@Path("id") id: Int): ApiResponse<Unit>
}
