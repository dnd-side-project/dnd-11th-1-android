package com.materip.core_datastore.test_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.response.TestResponseDto

interface TestDataStore {
    suspend fun getTest(): ResultResponse<Any>
    suspend fun postTest(): ResultResponse<TestResponseDto>
}