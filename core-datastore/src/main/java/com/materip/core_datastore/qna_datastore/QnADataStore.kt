package com.materip.core_datastore.qna_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.DefaultIdsRequestDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.request.PagingRequestIntDto
import com.materip.core_model.request.QnARequestDto
import com.materip.core_model.response.DefaultPagingResponseDto
import com.materip.core_model.response.QnAResponseDto

interface QnADataStore {
    suspend fun postQnA(requestDto: QnARequestDto): ResultResponse<Any>
    suspend fun getQnA(requestDto: PagingRequestIntDto): ResultResponse<DefaultPagingResponseDto<QnAResponseDto>>
    suspend fun deleteQnA(requestDto: DefaultIdsRequestDto): ResultResponse<Any>
}