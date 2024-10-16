package com.materip.core_datastore.qna_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.DefaultIdsRequestDto
import com.materip.core_model.request.PagingRequestDto
import com.materip.core_model.request.PagingRequestIntDto
import com.materip.core_model.request.QnARequestDto
import com.materip.core_model.response.DefaultPagingResponseDto
import com.materip.core_model.response.QnAResponseDto
import com.materip.core_network.service.qna.QnA100Service
import com.skydoves.sandwich.message
import com.skydoves.sandwich.retrofit.apiMessage
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class QnADataStoreImpl @Inject constructor(
    private val qnA100Service: QnA100Service
): QnADataStore{
    override suspend fun postQnA(requestDto: QnARequestDto): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        qnA100Service.postQnA(requestDto).suspendOnError{
            result.error = Json.decodeFromString(this.message())
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun getQnA(requestDto: PagingRequestIntDto): ResultResponse<DefaultPagingResponseDto<QnAResponseDto>> {
        val result = ResultResponse<DefaultPagingResponseDto<QnAResponseDto>>()
        qnA100Service.getQnA(requestDto).suspendOnError{
            result.error = Json.decodeFromString(this.message())
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun deleteQnA(requestDto: DefaultIdsRequestDto): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        qnA100Service.deleteQnA(requestDto).suspendOnError{
            result.error = Json.decodeFromString(this.message())
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
}