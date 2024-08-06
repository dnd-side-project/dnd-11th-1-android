package com.materip.core_network.networkDI

import android.util.Log
import com.google.gson.Gson
import com.materip.core_database.TokenManager
import com.materip.core_network.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
) : okhttp3.Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val originRequest = response.request

        if (originRequest.header("X-AUTH_TOKEN").isNullOrEmpty()){
            return null
        }
        val refreshToken = runBlocking{
            tokenManager.getAuthTokenForHeader()
        }
        val refreshRequest = Request.Builder()
            .url(BuildConfig.BASE_URL)
            .post("".toRequestBody())
            .addHeader("X-AUTH-TOKEN", "${refreshToken}")
            .build()
        val refreshResponse = OkHttpClient().newCall(refreshRequest).execute()

        if (refreshResponse.code == 201) {
            val gson = Gson()
            val refreshResponseJson = gson.fromJson(refreshResponse.body?.string(), Map::class.java)
            val newAccessToken = refreshResponseJson["accessToken"].toString()
            CoroutineScope(Dispatchers.IO).launch{
                tokenManager.deleteAuthToken()
                tokenManager.saveAuthToken(newAccessToken)
            }
            val newRequest = originRequest.newBuilder()
                .removeHeader("X-AUTH-TOKEN")
                .addHeader("X-AUTH-TOKEN", newAccessToken)
                .build()
            return newRequest
        } else {
            Log.d("REFRESH TOKEN", "refresh 토큰 만료")
            CoroutineScope(Dispatchers.IO).launch{
                tokenManager.deleteAuthToken()
                tokenManager.deleteRefreshToken()
            }
            return null
        }
    }
}