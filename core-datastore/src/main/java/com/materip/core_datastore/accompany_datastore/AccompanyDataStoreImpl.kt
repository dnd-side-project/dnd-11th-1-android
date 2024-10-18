package com.materip.core_datastore.accompany_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.accompany_board.all.BoardItem
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.request.PagingRequestIntDto
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_model.response.BoardItemWithRequestId
import com.materip.core_model.response.BoardItemWithReviewId
import com.materip.core_model.response.DefaultPagingResponseDto
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

    override suspend fun postCancel(id: Int): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        accompanyService.postCancel(id).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess {
            result.data = this.data
        }
        return result
    }

    override suspend fun postAccompanySend(requestDto: PagingRequestDto): ResultResponse<DefaultPagingResponseDto<BoardItemWithRequestId>> {
        val result = ResultResponse<DefaultPagingResponseDto<BoardItemWithRequestId>>()
        accompanyService.postAccompanySend(requestDto).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun getAccompanyReceived(requestDto: PagingRequestDto): ResultResponse<DefaultPagingResponseDto<AccompanyReceivedItem>> {
        val result = ResultResponse<DefaultPagingResponseDto<AccompanyReceivedItem>>()
        accompanyService.getAccompanyReceived(requestDto).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
    override suspend fun getAccompanyRecords(requestDto: PagingRequestDto): ResultResponse<DefaultPagingResponseDto<BoardItemWithReviewId>> {
        val result = ResultResponse<DefaultPagingResponseDto<BoardItemWithReviewId>>()
        accompanyService.getAccompanyRecords(requestDto).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun getAccompanyMyPost(requestDto: PagingRequestDto): ResultResponse<DefaultPagingResponseDto<BoardItem>> {
        val result = ResultResponse<DefaultPagingResponseDto<BoardItem>>()
        accompanyService.getAccompanyMyPost(requestDto).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun postReject(id: Int): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        accompanyService.postReject(id).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess {
            result.data = this.data
        }
        return result
    }

    override suspend fun postAccept(id: Int): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        accompanyService.postAccept(id).suspendOnError{
            result.error = Json.decodeFromString("${this.apiMessage}")
        }.suspendOnSuccess {
            result.data = this.data
        }
        return result
    }
}