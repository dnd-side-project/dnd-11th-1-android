package com.materip.core_repository.repository.qna_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.qna_datastore.QnADataStore
import com.materip.core_model.request.DefaultIdsRequestDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.request.PagingRequestIntDto
import com.materip.core_model.request.QnARequestDto
import com.materip.core_model.response.DefaultPagingResponseDto
import com.materip.core_model.response.QnAResponseDto
import javax.inject.Inject

class QnARepositoryImpl @Inject constructor(
    private val qnADataStore: QnADataStore
): QnARepository {
    override suspend fun postQnA(requestDto: QnARequestDto): ResultResponse<Any> {
        return qnADataStore.postQnA(requestDto)
    }

    override suspend fun getQnA(requestDto: PagingRequestIntDto): ResultResponse<DefaultPagingResponseDto<QnAResponseDto>> {
        return qnADataStore.getQnA(requestDto)
    }

    override suspend fun deleteQnA(requestDto: DefaultIdsRequestDto): ResultResponse<Any> {
        return qnADataStore.deleteQnA(requestDto)
    }
}