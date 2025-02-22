package com.materip.core_repository.repository.accompany_repository

import com.materip.core_common.ResultResponse
import com.materip.core_model.accompany_board.all.BoardItem
import com.materip.core_model.request.AccompanyApplicationResponseDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.response.AccompanyReceivedItem
import com.materip.core_model.response.BoardItemWithRequestId
import com.materip.core_model.response.BoardItemWithReviewId
import com.materip.core_model.response.DefaultPagingResponseDto

interface AccompanyRepository {
    suspend fun getAccompanyApplication(id: Int): ResultResponse<AccompanyApplicationResponseDto>
    suspend fun postCancel(id: Int): ResultResponse<Any>
    suspend fun postAccompanySend(requestDto: PagingRequestDto): ResultResponse<DefaultPagingResponseDto<BoardItemWithRequestId>>
    suspend fun postAccompanyReceived(requestDto: PagingRequestDto): ResultResponse<DefaultPagingResponseDto<AccompanyReceivedItem>>
    suspend fun postAccompanyRecords(requestDto: PagingRequestDto): ResultResponse<DefaultPagingResponseDto<BoardItemWithReviewId>>
    suspend fun getAccompanyMyPost(requestDto: PagingRequestDto): ResultResponse<DefaultPagingResponseDto<BoardItem>>
    suspend fun postReject(id: Int): ResultResponse<Any>
    suspend fun postAccept(id: Int): ResultResponse<Any>
}