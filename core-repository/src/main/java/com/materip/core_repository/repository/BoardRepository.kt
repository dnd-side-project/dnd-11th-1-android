package com.materip.core_repository.repository
// repository모듈은 데이터 소스와의 상호작용을 추상화함

import com.materip.core_common.ResultResponse
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.accompany_board.BoardListResponse
import com.materip.core_model.accompany_board.Pageable
import com.materip.core_model.accompany_board.id.BoardIdDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto
import com.materip.core_model.accompany_board.request.CompanionRequest

// 게시글 생성 메서드를 정의
interface BoardRepository {
    suspend fun getBoard(pageable: Pageable): ResultResponse<BoardListResponse>
    suspend fun postBoard(board: BoardRequestDto): ResultResponse<BoardIdDto>
    suspend fun getBoardDetail(id: Int): ResultResponse<GetBoardDetailDto>
    suspend fun postCompanionRequest(companionRequest: CompanionRequest): ResultResponse<Unit>
}