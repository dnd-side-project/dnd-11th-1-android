package com.materip.core_datastore.qna_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.QnARequestDto
import com.materip.core_model.response.GetQnAResponseDto

interface QnADataStore {
    suspend fun postQnA(requestDto: QnARequestDto): ResultResponse<Any>
    suspend fun getQnA(): ResultResponse<GetQnAResponseDto>
    suspend fun deleteQnA(ids: Array<Int>): ResultResponse<Any>
}