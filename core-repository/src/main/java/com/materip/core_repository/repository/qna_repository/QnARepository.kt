package com.materip.core_repository.repository.qna_repository

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.QnARequestDto
import com.materip.core_model.response.GetQnAResponseDto

interface QnARepository {
    suspend fun postQnA(requestDto: QnARequestDto): ResultResponse<Any>
    suspend fun getQnA(): ResultResponse<GetQnAResponseDto>
    suspend fun deleteQnA(ids: Array<Int>): ResultResponse<Any>
}