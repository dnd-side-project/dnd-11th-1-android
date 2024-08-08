package com.materip.core_datastore.com.materip.core_datastore.login_datastore

import kotlinx.coroutines.flow.Flow

interface LocalLoginDataStore {
    suspend fun getAuthToken(): Flow<String?>
    suspend fun getRefreshToken(): Flow<String?>
    suspend fun getKakaoAccessToken(): Flow<String?>
    suspend fun saveAuthToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun saveKakaoAccessToken(token: String)
    suspend fun deleteAuthToken()
    suspend fun deleteRefreshToken()
    suspend fun deleteKakaoAccessToken()
}