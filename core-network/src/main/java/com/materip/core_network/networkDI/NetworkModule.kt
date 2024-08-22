package com.materip.core_network.networkDI

import com.google.gson.GsonBuilder
import com.materip.core_database.TokenManager
import com.materip.core_network.networkDI.AuthAuthenticator
import com.materip.core_network.networkDI.HeaderInterceptor
import com.materip.core_network.service.image.ImageService
import com.materip.core_network.BuildConfig
import com.materip.core_network.service.accompany.AccompanyService
import com.materip.core_network.service.home.BoardService
import com.materip.core_network.service.login.LoginService
import com.materip.core_network.service.onboarding.OnboardingService
import com.materip.core_network.service.profile.ProfileService
import com.materip.core_network.service.test.TestService
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

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
    @Singleton
    @Provides
    fun provideOnboardingService(retrofit: Retrofit): OnboardingService {
        return retrofit.create(OnboardingService::class.java)
    }
    @Singleton
    @Provides
    fun provideProfileService(retrofit: Retrofit): ProfileService{
        return retrofit.create(ProfileService::class.java)
    }
    @Singleton
    @Provides
    fun provideImageService(retrofit: Retrofit): ImageService {
        return retrofit.create(ImageService::class.java)
    }

    @Provides
    @Singleton
    fun provideBoardService(retrofit: Retrofit): BoardService {
        return retrofit.create(BoardService::class.java)
    }

    @Provides
    @Singleton
    fun provideAccompanyService(retrofit: Retrofit): AccompanyService{
        return retrofit.create(AccompanyService::class.java)
    }
}