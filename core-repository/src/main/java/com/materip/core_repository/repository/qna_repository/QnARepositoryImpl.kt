package com.materip.core_repository.repository.qna_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.qna_datastore.QnADataStore
import com.materip.core_model.request.QnARequestDto
import com.materip.core_model.response.GetQnAResponseDto
import javax.inject.Inject

class QnARepositoryImpl @Inject constructor(
    private val qnADataStore: QnADataStore
): QnARepository {
    override suspend fun postQnA(requestDto: QnARequestDto): ResultResponse<Any> {
        return qnADataStore.postQnA(requestDto)
    }

    override suspend fun getQnA(): ResultResponse<GetQnAResponseDto> {
        return qnADataStore.getQnA()
    }

    override suspend fun deleteQnA(ids: Array<Int>): ResultResponse<Any> {
        return qnADataStore.deleteQnA(ids)
    }
}