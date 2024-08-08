package com.materip.core_datastore

import com.materip.core_database.TokenManager
import com.materip.core_database.TokenManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalPreferenceManagerModule {
    @Binds
    @Singleton
    fun bindTokenManager(tokenManagerImpl: TokenManagerImpl): TokenManager
}