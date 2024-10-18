package com.materip.core_repository.repository.accompany_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.accompany_datastore.AccompanyDataStore
import com.materip.core_model.accompany_board.all.BoardItem
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.request.PagingRequestIntDto
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_model.response.BoardItemWithRequestId
import com.materip.core_model.response.BoardItemWithReviewId
import com.materip.core_model.response.DefaultPagingResponseDto
import javax.inject.Inject

class AccompanyRepositoryImpl @Inject constructor(
    private val accompanyDataStore: AccompanyDataStore
): AccompanyRepository{
    override suspend fun getAccompanyApplication(
        id: Int
    ): ResultResponse<AccompanyApplicationResponseDto> {
        return accompanyDataStore.getAccompanyApplication(id)
    }

    override suspend fun postCancel(id: Int): ResultResponse<Any> {
        return accompanyDataStore.postCancel(id)
    }

    override suspend fun postAccompanySend(requestDto: PagingRequestDto): ResultResponse<DefaultPagingResponseDto<BoardItemWithRequestId>> {
        return accompanyDataStore.postAccompanySend(requestDto)
    }

    override suspend fun postAccompanyReceived(requestDto: PagingRequestDto): ResultResponse<DefaultPagingResponseDto<AccompanyReceivedItem>> {
        return accompanyDataStore.postAccompanyReceived(requestDto)
    }

    override suspend fun postAccompanyRecords(requestDto: PagingRequestDto): ResultResponse<DefaultPagingResponseDto<BoardItemWithReviewId>> {
        return accompanyDataStore.postAccompanyRecords(requestDto)
    }

    override suspend fun getAccompanyMyPost(requestDto: PagingRequestDto): ResultResponse<DefaultPagingResponseDto<BoardItem>> {
        return accompanyDataStore.getAccompanyMyPost(requestDto)
    }

    override suspend fun postReject(id: Int): ResultResponse<Any> {
        return accompanyDataStore.postReject(id)
    }

    override suspend fun postAccept(id: Int): ResultResponse<Any> {
        return accompanyDataStore.postAccept(id)
    }
}