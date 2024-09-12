package com.materip.core_datastore.accompany_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.accompany_board.all.BoardItem
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_model.response.BoardItemWithRequestId
import com.materip.core_model.response.BoardItemWithReviewId
import com.materip.core_model.response.DefaultGetAccompanyResponseDto

interface AccompanyDataStore {
    suspend fun getAccompanyApplication(id: Int): ResultResponse<AccompanyApplicationResponseDto>
    suspend fun getAccompanySend(requestDto: PagingRequestDto): ResultResponse<DefaultGetAccompanyResponseDto<BoardItemWithRequestId>>
    suspend fun getAccompanyReceived(requestDto: PagingRequestDto): ResultResponse<DefaultGetAccompanyResponseDto<AccompanyReceivedItem>>
    suspend fun getAccompanyRecords(requestDto: PagingRequestDto): ResultResponse<DefaultGetAccompanyResponseDto<BoardItemWithReviewId>>
    suspend fun getAccompanyMyPost(requestDto: PagingRequestDto): ResultResponse<DefaultGetAccompanyResponseDto<BoardItem>>
}