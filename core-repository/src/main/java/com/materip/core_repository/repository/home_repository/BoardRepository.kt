package com.materip.core_repository.repository.home_repository
// repository모듈은 데이터 소스와의 상호작용을 추상화함

import com.materip.core_common.ResultResponse
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.accompany_board.BoardListResponse
import com.materip.core_model.accompany_board.Pageable
import com.materip.core_model.accompany_board.id.BoardIdDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto
import com.materip.core_model.accompany_board.notification.Notification
import com.materip.core_model.accompany_board.profile.GetUserProfile
import com.materip.core_model.accompany_board.request.CompanionRequest

// 게시글 생성 메서드를 정의
interface BoardRepository {
    // 동행글 목록 조회
    suspend fun getBoard(pageable: Pageable): ResultResponse<BoardListResponse>
    // 동행글 생성
    suspend fun postBoard(board: BoardRequestDto): ResultResponse<BoardIdDto>
    // 동행글 상세 조회
    suspend fun getBoardDetail(id: Int): ResultResponse<GetBoardDetailDto>
    // 동행 신청
    suspend fun postCompanionRequest(companionRequest: CompanionRequest): ResultResponse<Unit>
    // 동행글 삭제
    suspend fun deleteBoard(id: Int): ResultResponse<Unit>
    // 프로필 조회
    suspend fun getUserProfile(): ResultResponse<GetUserProfile>

}