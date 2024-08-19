package com.materip.core_datastore.home_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.accompany_board.BoardListResponse
import com.materip.core_model.accompany_board.Pageable
import com.materip.core_model.accompany_board.id.BoardIdDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto
import com.materip.core_model.accompany_board.request.CompanionRequest

interface BoardDataStore {
    suspend fun getBoard(pageable: Pageable): ResultResponse<BoardListResponse>
    suspend fun postBoard(board: BoardRequestDto): ResultResponse<BoardIdDto>
    suspend fun getBoardDetail(id: Int): ResultResponse<GetBoardDetailDto>
    suspend fun postCompanionRequest(companionRequest: CompanionRequest): ResultResponse<Unit>
}