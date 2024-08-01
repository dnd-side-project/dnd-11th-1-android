package com.materip.core_database

import kotlinx.coroutines.flow.Flow

interface TokenManager {
    suspend fun getAuthToken(): Flow<String?>
    suspend fun getRefreshToken(): Flow<String?>
    suspend fun getKakaoAccessToken(): Flow<String?>
    suspend fun saveAuthToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun saveKakaoAccessToken(token: String)
}