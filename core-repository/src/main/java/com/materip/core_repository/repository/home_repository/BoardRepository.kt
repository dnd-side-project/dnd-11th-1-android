package com.materip.core_repository.repository.home_repository

import com.materip.core_common.ResultResponse
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

interface BoardRepository {
    // 동행글 목록 조회
    suspend fun getBoard(boardRequest: PagingRequestDto): ResultResponse<BoardListResponse>
    // 동행글 생성
    suspend fun postBoard(board: BoardRequestDto): ResultResponse<BoardIdDto>
    // 동행글 상세 조회
    suspend fun getBoardDetail(id: Int): ResultResponse<GetBoardDetailDto>
    // 동행 신청
    suspend fun postCompanionRequest(companionRequest: CompanionRequest): ResultResponse<Unit>
    // 동행글 삭제
    suspend fun deleteBoard(id: Int): ResultResponse<Unit>
    // 유저 프로필 상세 조회
    suspend fun getUserProfile(userId: Int): ResultResponse<GetUserProfile>
    // 동행글 검색
    suspend fun searchBoardList(query: QueryRequestDto): ResultResponse<BoardListResponse>
    // 내가 쓴 동행글 목록 조회
    suspend fun getMyBoardList(request: GetAccompanyBoard): ResultResponse<AccompanyBoardList>
    // 동행 시작 여부에 따른 동행글 목록 조회
    suspend fun getBoardListByCondition(
        region: String?,
        started: Boolean,
        recruited: Boolean,
        boardRequest: PagingRequestDto
    ): ResultResponse<BoardListResponse>
}