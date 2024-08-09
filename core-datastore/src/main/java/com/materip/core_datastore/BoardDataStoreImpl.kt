package com.materip.core_datastore

import com.materip.core_common.ResponseError
import com.materip.core_common.ResultResponse
import com.materip.core_model.BoardRequestDto
import com.materip.core_model.BoardResponseDto
import com.materip.core_network.service.BoardService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject


class BoardDataStoreImpl @Inject constructor(
    private val boardService: BoardService
) : BoardDataStore {
    override suspend fun getBoard(): ResultResponse<BoardResponseDto> {
        val result = ResultResponse<BoardResponseDto>()
        boardService.getBoard().suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.error = Json.decodeFromString<ResponseError>(this.message())
        }
        return result
    }

    override suspend fun postBoard(board: BoardRequestDto): ResultResponse<BoardRequestDto> {
        val result = ResultResponse<BoardRequestDto>()
        boardService.postBoard(board).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.error = Json.decodeFromString<ResponseError>(this.message())
        }
        return result
    }
}
