package com.materip.core_datastore.login_datastore

import com.materip.core_database.TokenManager
import com.materip.core_datastore.com.materip.core_datastore.login_datastore.LocalLoginDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalLoginDataStoreImpl @Inject constructor(
    private val tokenManager: TokenManager
): LocalLoginDataStore {
    override suspend fun getAuthToken(): Flow<String?> {
        return tokenManager.getAuthToken()
    }

    override suspend fun getRefreshToken(): Flow<String?> {
        return tokenManager.getRefreshToken()
    }

    override suspend fun getKakaoAccessToken(): Flow<String?> {
        return tokenManager.getKakaoAccessToken()
    }

    override suspend fun saveAuthToken(token: String) {
        tokenManager.saveAuthToken(token)
    }

    override suspend fun saveRefreshToken(token: String) {
        tokenManager.saveRefreshToken(token)
    }

    override suspend fun saveKakaoAccessToken(token: String) {
        tokenManager.saveKakaoAccessToken(token)
    }

    override suspend fun deleteAuthToken() {
        tokenManager.deleteAuthToken()
    }

    override suspend fun deleteRefreshToken() {
        tokenManager.deleteRefreshToken()
    }

    override suspend fun deleteKakaoAccessToken() {
        tokenManager.deleteKakaoAccessToken()
    }
}