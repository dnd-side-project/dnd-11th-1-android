package com.materip.core_datastore.home_datastore

import com.materip.core_common.ResponseError
import com.materip.core_common.ResultResponse
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
import com.materip.core_network.service.home.BoardService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.retrofit.apiMessage
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject


class BoardDataStoreImpl @Inject constructor(
    private val boardService: BoardService
) : BoardDataStore {
    override suspend fun getBoard(boardRequest: PagingRequestDto): ResultResponse<BoardListResponse> {
        val result = ResultResponse<BoardListResponse>()
        boardService.getBoard(boardRequest).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }
        return result
    }

    override suspend fun postBoard(board: BoardRequestDto): ResultResponse<BoardIdDto> {
        val result = ResultResponse<BoardIdDto>()
        boardService.postBoard(board).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }
        return result
    }

    override suspend fun getBoardDetail(id: Int): ResultResponse<GetBoardDetailDto> {
        val result = ResultResponse<GetBoardDetailDto>()
        boardService.getBoardDetail(id).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }
        return result
    }

    override suspend fun postCompanionRequest(companionRequest: CompanionRequest): ResultResponse<Unit> {
        val result = ResultResponse<Unit>()
        boardService.postCompanionRequest(companionRequest).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }
        return result
    }

    override suspend fun deleteBoard(id: Int): ResultResponse<Unit> {
        val result = ResultResponse<Unit>()
        boardService.deleteBoard(id).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }
        return result
    }

    override suspend fun getUserProfile(): ResultResponse<GetUserProfile> {
        val result = ResultResponse<GetUserProfile>()
        boardService.getProfile().suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }
        return result
    }

    override suspend fun searchBoardList(query: QueryRequestDto): ResultResponse<BoardListResponse> {
        val result = ResultResponse<BoardListResponse>()
        boardService.searchBoardList(query).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.error = Json.decodeFromString<ResponseError>(this.message())
        }
        return result
    }

    override suspend fun getMyBoardList(boardRequest: GetAccompanyBoard): ResultResponse<AccompanyBoardList> {
        val result = ResultResponse<AccompanyBoardList>()
        boardService.getMyBoardList(boardRequest).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }
        return result
    }
}
