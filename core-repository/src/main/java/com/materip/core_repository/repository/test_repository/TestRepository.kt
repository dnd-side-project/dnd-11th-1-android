package com.materip.core_repository.repository.test_repository

import com.materip.core_common.ResultResponse
import com.materip.core_model.response.TestResponseDto

interface TestRepository {
    suspend fun getTest(): ResultResponse<Any>
    suspend fun postTest(): ResultResponse<TestResponseDto>
}