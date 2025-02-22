package com.materip.core_network.service.home

import com.materip.core_model.accompany_board.all.BoardListResponse
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.accompany_board.id.BoardIdDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto
import com.materip.core_model.accompany_board.mine.AccompanyBoardList
import com.materip.core_model.accompany_board.mine.GetAccompanyBoard
import com.materip.core_model.accompany_board.profile.GetUserProfile
import com.materip.core_model.accompany_board.request.CompanionRequest
import com.materip.core_model.accompany_board.search.QueryRequestDto
import com.materip.core_model.request.PagingRequestDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardService {
    // 동행글 목록 조회
    @POST("/api/v1/accompany/boards/all")
    suspend fun getBoard(
        @Body requestDto: PagingRequestDto
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

    // 유저 프로필 상세 조회
    @GET("/api/v1/profiles/{userId}")
    suspend fun getProfile(@Path("userId") userId: Int): ApiResponse<GetUserProfile>

    // 동행글 삭제
    @POST("/api/v1/accompany/boards/remove/{id}")
    suspend fun deleteBoard(@Path("id") id: Int): ApiResponse<Unit>

    // 동행글 검색
    @POST("/api/v1/accompany/boards/search")
    suspend fun searchBoardList(@Body queryRequestDto: QueryRequestDto): ApiResponse<BoardListResponse>

    // 내가 쓴 동행글 목록 조회
    @POST("/api/v1/accompany/boards/mine")
    suspend fun getMyBoardList(@Body requestDto: GetAccompanyBoard): ApiResponse<AccompanyBoardList>

    // 동행 시작 여부에 따른 동행글 목록 조회
    @POST("/api/v1/accompany/boards/all/by-condition")
    suspend fun getBoardListByCondition(
        @Query("region") region: String? = null,
        @Query("started") started: Boolean,
        @Query("recruited") recruited: Boolean,
        @Body requestDto: PagingRequestDto
    ): ApiResponse<BoardListResponse>
}
