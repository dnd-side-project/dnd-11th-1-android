package com.materip.core_repository.repository.home_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.home_datastore.BoardDataStore
import com.materip.core_model.accompany_board.all.BoardListResponse
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.accompany_board.id.BoardIdDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto
import com.materip.core_model.accompany_board.mine.AccompanyBoardList
import com.materip.core_model.accompany_board.mine.GetAccompanyBoard
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

    override suspend fun getUserProfile(userId: Int): ResultResponse<GetUserProfile> {
        return boardDataStore.getUserProfile(userId)
    }

    override suspend fun searchBoardList(query: QueryRequestDto): ResultResponse<BoardListResponse> {
        return boardDataStore.searchBoardList(query)
    }

    override suspend fun getMyBoardList(request: GetAccompanyBoard): ResultResponse<AccompanyBoardList> {
        return boardDataStore.getMyBoardList(request)
    }

    override suspend fun getBoardListByStarted(region: String?, started: Boolean): ResultResponse<BoardListResponse> {
        return boardDataStore.getBoardListByStarted(region, started)
    }
}