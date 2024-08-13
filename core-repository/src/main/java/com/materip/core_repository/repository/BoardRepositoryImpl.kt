package com.materip.core_repository.repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.BoardDataStore
import com.materip.core_model.accompany_board.BoardRequestDto
import com.materip.core_model.accompany_board.BoardResponseDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val boardDataStore: BoardDataStore
) : BoardRepository {
    override suspend fun getBoard(): ResultResponse<BoardResponseDto> {
        return boardDataStore.getBoard()
    }

    override suspend fun postBoard(board: BoardRequestDto): ResultResponse<BoardRequestDto> {
        return boardDataStore.postBoard(board)
    }

    override suspend fun getBoardDetail(id: Int): ResultResponse<GetBoardDetailDto> {
        return boardDataStore.getBoardDetail(id)
    }
}