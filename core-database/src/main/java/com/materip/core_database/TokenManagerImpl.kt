package com.materip.core_database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    corruptionHandler = ReplaceFileCorruptionHandler{
        it.printStackTrace()
        emptyPreferences()
    },
    scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    name = "com.materip.core_database"
)

class TokenManagerImpl @Inject constructor(
    @ApplicationContext context: Context
): TokenManager{
    private val dataStore = context.datastore

    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("AUTH_TOKEN")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("REMEMBERED_TOKEN")
        private val KAKAO_ACCESS_TOKEN_KEY = stringPreferencesKey("KAKAO_ACCESS_TOKEN")
    }
    override suspend fun getAuthTokenForHeader(): String? {
        val authToken = runBlocking{
            getAuthToken().firstOrNull()
        }
        return authToken
    }

    override suspend fun getAuthToken(): Flow<String?> = dataStore.data.map{it[AUTH_TOKEN_KEY]}
    override suspend fun getRefreshToken(): Flow<String?> = dataStore.data.map{it[REFRESH_TOKEN_KEY]}
    override suspend fun getKakaoAccessToken(): Flow<String?> = dataStore.data.map{it[KAKAO_ACCESS_TOKEN_KEY]}
    override suspend fun saveAuthToken(token: String) {
        dataStore.edit { it[AUTH_TOKEN_KEY] = token }
    }
    override suspend fun saveRefreshToken(token: String) {
        dataStore.edit { it[REFRESH_TOKEN_KEY] = token }
    }
    override suspend fun saveKakaoAccessToken(token: String) {
        dataStore.edit{it[KAKAO_ACCESS_TOKEN_KEY] = token}
    }
    override suspend fun deleteAuthToken() {
        dataStore.edit{it.remove(AUTH_TOKEN_KEY)}
    }

    override suspend fun deleteRefreshToken() {
        dataStore.edit{it.remove(REFRESH_TOKEN_KEY)}
    }

    override suspend fun deleteKakaoAccessToken() {
        dataStore.edit{it.remove(KAKAO_ACCESS_TOKEN_KEY)}
    }
}