package com.materip.core_network.networkDI

import com.materip.core_database.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val tokenManager: TokenManager
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking{
            tokenManager.getAuthTokenForHeader()
        }
        val bearerToken = if(token == null) "" else "Bearer ${token}"
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", bearerToken)
            .build()
        val response = chain.proceed(newRequest)

        return response
    }
}