package com.materip.core_repository.repository.login_repository

import com.materip.core_datastore.com.materip.core_datastore.login_datastore.LocalLoginDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val localLoginDataStore: LocalLoginDataStore
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
}