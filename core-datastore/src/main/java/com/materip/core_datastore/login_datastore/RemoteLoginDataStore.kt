package com.materip.core_datastore.com.materip.core_datastore.login_datastore

import com.materip.core_common.ResultResponse
import com.materip.core_model.request.DeleteAccountRequestDto
import com.materip.core_model.request.LoginRequestDto
import com.materip.core_model.response.LoginResponseDto

interface RemoteLoginDataStore {
    suspend fun loginKakao(requestDto: LoginRequestDto): ResultResponse<LoginResponseDto>
    suspend fun deleteAccount(requestDto: DeleteAccountRequestDto): ResultResponse<Any>
}