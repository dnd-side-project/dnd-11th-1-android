package com.materip.core_datastore.accompany_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.accompany_board.BoardItem
import com.materip.core_model.accompany_board.Pageable
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_model.response.DefaultGetAccompanyResponseDto
import com.materip.core_network.service.accompany.AccompanyService
import com.skydoves.sandwich.retrofit.apiMessage
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AccompanyDataStoreImpl @Inject constructor(
    private val accompanyService: AccompanyService
): AccompanyDataStore {
    override suspend fun getAccompanyApplication(
        id: Int
    ): ResultResponse<AccompanyApplicationResponseDto> {
        val result = ResultResponse<AccompanyApplicationResponseDto>()
        accompanyService.getAccompanyApplication(id).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun getAccompanySend(pageable: Pageable): ResultResponse<DefaultGetAccompanyResponseDto<BoardItem>> {
        val result = ResultResponse<DefaultGetAccompanyResponseDto<BoardItem>>()
        accompanyService.getAccompanySend(pageable).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun getAccompanyReceived(pageable: Pageable): ResultResponse<DefaultGetAccompanyResponseDto<AccompanyReceivedItem>> {
        val result = ResultResponse<DefaultGetAccompanyResponseDto<AccompanyReceivedItem>>()
        accompanyService.getAccompanyReceived(pageable).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
    override suspend fun getAccompanyRecords(pageable: Pageable): ResultResponse<DefaultGetAccompanyResponseDto<BoardItem>> {
        val result = ResultResponse<DefaultGetAccompanyResponseDto<BoardItem>>()
        accompanyService.getAccompanyRecords(pageable).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
}