package com.materip.core_repository.repository
// repository모듈은 데이터 소스와의 상호작용을 추상화함

import com.materip.core_common.ResultResponse
import com.materip.core_model.accompany_board.BoardRequestDto
import com.materip.core_model.accompany_board.BoardResponseDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto

// 게시글 생성 메서드를 정의
interface BoardRepository {
    suspend fun getBoard(): ResultResponse<BoardResponseDto>
    suspend fun postBoard(board: BoardRequestDto): ResultResponse<BoardRequestDto>
    suspend fun getBoardDetail(id: Int): ResultResponse<GetBoardDetailDto>
}