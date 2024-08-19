package com.materip.core_datastore.home_datastore

import com.materip.core_common.ResponseError
import com.materip.core_common.ResultResponse
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.accompany_board.BoardListResponse
import com.materip.core_model.accompany_board.Pageable
import com.materip.core_model.accompany_board.id.BoardIdDto
import com.materip.core_model.accompany_board.id.GetBoardDetailDto
import com.materip.core_model.accompany_board.request.CompanionRequest
import com.materip.core_network.service.home.BoardService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject


class BoardDataStoreImpl @Inject constructor(
    private val boardService: BoardService
) : BoardDataStore {
    override suspend fun getBoard(pageable: Pageable): ResultResponse<BoardListResponse> {
        val result = ResultResponse<BoardListResponse>()
        boardService.getBoard().suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.error = Json.decodeFromString<ResponseError>(this.message())
        }
        return result
    }

    override suspend fun postBoard(board: BoardRequestDto): ResultResponse<BoardIdDto> {
        val result = ResultResponse<BoardIdDto>()
        boardService.postBoard(board).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.error = Json.decodeFromString<ResponseError>(this.message())
        }
        return result
    }

    override suspend fun getBoardDetail(id: Int): ResultResponse<GetBoardDetailDto> {
        val result = ResultResponse<GetBoardDetailDto>()
        boardService.getBoardDetail(id).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.error = Json.decodeFromString<ResponseError>(this.message())
        }
        return result
    }

    override suspend fun postCompanionRequest(companionRequest: CompanionRequest): ResultResponse<Unit> {
        val result = ResultResponse<Unit>()
        boardService.postCompanionRequest(companionRequest).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.error = Json.decodeFromString<ResponseError>(this.message())
        }
        return result
    }
}
