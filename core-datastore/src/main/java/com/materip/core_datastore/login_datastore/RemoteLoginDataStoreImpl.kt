package com.materip.core_datastore.com.materip.core_datastore.login_datastore

import com.materip.core_common.ResponseError
import com.materip.core_common.ResultResponse
import com.materip.core_model.request.DeleteAccountRequestDto
import com.materip.core_model.request.LoginRequestDto
import com.materip.core_model.response.LoginResponseDto
import com.materip.core_network.service.login.LoginService
import com.skydoves.sandwich.retrofit.apiMessage
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RemoteLoginDataStoreImpl @Inject constructor(
    private val loginService: LoginService
): RemoteLoginDataStore{
    override suspend fun loginKakao(requestDto: LoginRequestDto): ResultResponse<LoginResponseDto> {
        val result = ResultResponse<LoginResponseDto>()
        loginService.loginKakao(requestDto).suspendOnError{
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun deleteAccount(requestDto: DeleteAccountRequestDto): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        loginService.deleteAccount(requestDto).suspendOnError{
            result.error = Json.decodeFromString<ResponseError>("${this.apiMessage}")
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
}