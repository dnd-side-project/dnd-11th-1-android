package com.materip.core_datastore.test_datastore

import com.materip.core_common.ResponseError
import com.materip.core_common.ResultResponse
import com.materip.core_model.response.TestResponseDto
import com.materip.core_network.service.test.TestService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TestDataStoreImpl @Inject constructor(
    private val testService: TestService
): TestDataStore{
    override suspend fun getTest(): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        testService.getTest().suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.error = Json.decodeFromString<ResponseError>(this.message())
        }
        return result
    }
    override suspend fun postTest(): ResultResponse<TestResponseDto> {
        val result = ResultResponse<TestResponseDto>()
        testService.postTest().suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.error = Json.decodeFromString<ResponseError>(this.message())
        }
        return result
    }
}