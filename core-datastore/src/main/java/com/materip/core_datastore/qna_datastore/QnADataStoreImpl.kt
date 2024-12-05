package com.materip.core_datastore.qna_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.QnARequestDto
import com.materip.core_model.response.GetQnAResponseDto
import com.materip.core_network.service.qna.QnA100Service
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
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

    override suspend fun getQnA(): ResultResponse<GetQnAResponseDto> {
        val result = ResultResponse<GetQnAResponseDto>()
        qnA100Service.getQnA().suspendOnError{
            result.error = Json.decodeFromString(this.message())
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun deleteQnA(ids: Array<Int>): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        qnA100Service.deleteQnA(ids).suspendOnError{
            result.error = Json.decodeFromString(this.message())
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
}