package com.materip.core_repository.repository.test_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.test_datastore.TestDataStore
import com.materip.core_model.response.TestResponseDto
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val testDataStore: TestDataStore
): TestRepository{
    override suspend fun getTest(): ResultResponse<Any> {
        return testDataStore.getTest()
    }
    override suspend fun postTest(): ResultResponse<TestResponseDto> {
        return testDataStore.postTest()
    }
}