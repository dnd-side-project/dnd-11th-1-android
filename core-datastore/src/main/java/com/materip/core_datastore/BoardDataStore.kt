package com.materip.core_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.BoardRequestDto
import com.materip.core_model.BoardResponseDto

interface BoardDataStore {
    suspend fun getBoard(): ResultResponse<BoardResponseDto>
    suspend fun postBoard(board: BoardRequestDto): ResultResponse<BoardRequestDto>
}