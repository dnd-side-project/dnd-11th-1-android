package com.materip.core_repository.repository.login_repository

import com.materip.core_common.ResultResponse
import com.materip.core_datastore.com.materip.core_datastore.login_datastore.LocalLoginDataStore
import com.materip.core_datastore.com.materip.core_datastore.login_datastore.RemoteLoginDataStore
import com.materip.core_model.request.DeleteAccountRequestDto
import com.materip.core_model.request.LoginRequestDto
import com.materip.core_model.response.LoginResponseDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val localLoginDataStore: LocalLoginDataStore,
    private val remoteLoginDataStore: RemoteLoginDataStore
): LoginRepository{
    override suspend fun getAuthToken(): Flow<String?> {
        return localLoginDataStore.getAuthToken()
    }

    override suspend fun getRefreshToken(): Flow<String?> {
        return localLoginDataStore.getRefreshToken()
    }

    override suspend fun getKakaoAccessToken(): Flow<String?> {
        return localLoginDataStore.getKakaoAccessToken()
    }

    override suspend fun saveAuthToken(token: String) {
        localLoginDataStore.saveAuthToken(token)
    }

    override suspend fun saveRefreshToken(token: String) {
        localLoginDataStore.saveRefreshToken(token)
    }

    override suspend fun saveKakaoAccessToken(token: String) {
        localLoginDataStore.saveKakaoAccessToken(token)
    }

    override suspend fun deleteAuthToken() {
        localLoginDataStore.deleteAuthToken()
    }

    override suspend fun deleteRefreshToken() {
        localLoginDataStore.deleteRefreshToken()
    }

    override suspend fun deleteKakaoToken() {
        localLoginDataStore.deleteKakaoAccessToken()
    }

    override suspend fun loginKakao(requestDto: LoginRequestDto): ResultResponse<LoginResponseDto> {
        return remoteLoginDataStore.loginKakao(requestDto)
    }

    override suspend fun deleteAccount(requestDto: DeleteAccountRequestDto): ResultResponse<Any> {
        return remoteLoginDataStore.deleteAccount(requestDto)
    }
}