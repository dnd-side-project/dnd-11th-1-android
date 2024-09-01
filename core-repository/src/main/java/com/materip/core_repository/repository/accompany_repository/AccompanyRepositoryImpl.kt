package com.materip.core_repository.repository.accompany_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.accompany_datastore.AccompanyDataStore
import com.materip.core_model.accompany_board.BoardItem
import com.materip.core_model.accompany_board.Pageable
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_model.response.BoardItemWithRequestId
import com.materip.core_model.response.BoardItemWithReviewId
import com.materip.core_model.response.DefaultGetAccompanyResponseDto
import javax.inject.Inject

class AccompanyRepositoryImpl @Inject constructor(
    private val accompanyDataStore: AccompanyDataStore
): AccompanyRepository{
    override suspend fun getAccompanyApplication(
        id: Int
    ): ResultResponse<AccompanyApplicationResponseDto> {
        return accompanyDataStore.getAccompanyApplication(id)
    }

    override suspend fun getAccompanySend(requestDto: PagingRequestDto): ResultResponse<DefaultGetAccompanyResponseDto<BoardItemWithRequestId>> {
        return accompanyDataStore.getAccompanySend(requestDto)
    }

    override suspend fun getAccompanyReceived(requestDto: PagingRequestDto): ResultResponse<DefaultGetAccompanyResponseDto<AccompanyReceivedItem>> {
        return accompanyDataStore.getAccompanyReceived(requestDto)
    }

    override suspend fun getAccompanyRecords(requestDto: PagingRequestDto): ResultResponse<DefaultGetAccompanyResponseDto<BoardItemWithReviewId>> {
        return accompanyDataStore.getAccompanyRecords(requestDto)
    }

    override suspend fun getAccompanyMyPost(requestDto: PagingRequestDto): ResultResponse<DefaultGetAccompanyResponseDto<BoardItem>> {
        return accompanyDataStore.getAccompanyMyPost(requestDto)
    }
}