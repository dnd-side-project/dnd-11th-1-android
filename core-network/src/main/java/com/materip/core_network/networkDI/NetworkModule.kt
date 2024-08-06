package com.materip.core_network

import com.google.gson.GsonBuilder
import com.materip.core_database.TokenManager
import com.materip.core_network.networkDI.AuthAuthenticator
import com.materip.core_network.networkDI.HeaderInterceptor
import com.materip.core_network.service.login.LoginService
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.materip.core_network.service.test.TestService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHeaderInterceptor(tokenManager: TokenManager): HeaderInterceptor {
        return HeaderInterceptor(tokenManager)
    }
    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): Authenticator{
        return AuthAuthenticator(tokenManager)
    }
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(headerInterceptor: HeaderInterceptor, authInterceptor: AuthAuthenticator): OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .authenticator(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideTestService(retrofit: Retrofit): TestService {
        return retrofit.create(TestService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }
}