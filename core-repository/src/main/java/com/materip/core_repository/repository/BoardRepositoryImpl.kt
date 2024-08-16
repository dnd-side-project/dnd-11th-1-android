package com.materip.core_repository.repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.BoardDataStore
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.accompany_board.BoardListResponse
import com.materip.core_model.accompany_board.Pageable
import com.materip.core_model.accompany_board.id.BoardIdDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto
import com.materip.core_model.accompany_board.request.CompanionRequest
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val boardDataStore: BoardDataStore
) : BoardRepository {
    override suspend fun getBoard(pageable: Pageable): ResultResponse<BoardListResponse> {
        return boardDataStore.getBoard(pageable)
    }

    override suspend fun postBoard(board: BoardRequestDto): ResultResponse<BoardIdDto> {
        return boardDataStore.postBoard(board)
    }

    override suspend fun getBoardDetail(id: Int): ResultResponse<GetBoardDetailDto> {
        return boardDataStore.getBoardDetail(id)
    }

    override suspend fun postCompanionRequest(companionRequest: CompanionRequest): ResultResponse<Unit> {
        return boardDataStore.postCompanionRequest(companionRequest)
    }
}