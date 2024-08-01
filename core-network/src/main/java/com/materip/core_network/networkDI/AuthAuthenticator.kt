package com.materip.core_network.networkDI

import com.materip.core_database.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
) : okhttp3.Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val newRequest: Request? = null
        return newRequest
    }

    fun Request.addRefreshAuthToken(token: String?): Request{
        return this.newBuilder().header("X-AUTH-TOKEN", "${token}").build()
    }
}