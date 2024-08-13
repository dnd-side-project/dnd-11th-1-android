package com.materip.core_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.accompany_board.BoardRequestDto
import com.materip.core_model.accompany_board.BoardResponseDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto

interface BoardDataStore {
    suspend fun getBoard(): ResultResponse<BoardResponseDto>
    suspend fun postBoard(board: BoardRequestDto): ResultResponse<BoardRequestDto>
    suspend fun getBoardDetail(id: Int): ResultResponse<GetBoardDetailDto>
}