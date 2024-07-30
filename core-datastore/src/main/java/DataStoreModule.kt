package com.materip.core_datastore

import com.materip.core_datastore.test_datastore.TestDataStore
import com.materip.core_datastore.test_datastore.TestDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {
    @Singleton
    @Binds
    fun provideTestDataStore(testDataStoreImpl: TestDataStoreImpl): TestDataStore
}