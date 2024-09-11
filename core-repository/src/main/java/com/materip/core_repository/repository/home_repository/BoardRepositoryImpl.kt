package com.materip.core_repository.repository.home_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.home_datastore.BoardDataStore
import com.materip.core_model.accompany_board.all.BoardListResponse
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.accompany_board.id.BoardIdDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto
import com.materip.core_model.accompany_board.profile.GetUserProfile
import com.materip.core_model.accompany_board.request.CompanionRequest
import com.materip.core_model.accompany_board.search.QueryRequestDto
import com.materip.core_model.accompany_board.search.SearchListResponse
import com.materip.core_model.request.PagingRequestDto
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val boardDataStore: BoardDataStore
) : BoardRepository {
    override suspend fun getBoard(boardRequest: PagingRequestDto): ResultResponse<BoardListResponse> {
        return boardDataStore.getBoard(boardRequest)
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

    override suspend fun deleteBoard(id: Int): ResultResponse<Unit> {
        return boardDataStore.deleteBoard(id)
    }

    override suspend fun getUserProfile(): ResultResponse<GetUserProfile> {
        return boardDataStore.getUserProfile()
    }

    override suspend fun searchBoardList(queryRequestDto: QueryRequestDto): ResultResponse<SearchListResponse> {
        return boardDataStore.searchBoardList(queryRequestDto)
    }
}