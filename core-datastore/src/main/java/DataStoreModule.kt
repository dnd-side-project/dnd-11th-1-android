package com.materip.core_datastore

import com.materip.core_datastore.com.materip.core_datastore.login_datastore.LocalLoginDataStore
import com.materip.core_datastore.com.materip.core_datastore.login_datastore.LocalLoginDataStoreImpl
import com.materip.core_datastore.com.materip.core_datastore.login_datastore.RemoteLoginDataStore
import com.materip.core_datastore.com.materip.core_datastore.login_datastore.RemoteLoginDataStoreImpl
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

    @Singleton
    @Binds
    fun provideLocalLoginDataStore(localLoginDataStoreImpl: LocalLoginDataStoreImpl): LocalLoginDataStore

    @Singleton
    @Binds
    fun provideRemoteLoginDataStore(remoteLoginDataStoreImpl: RemoteLoginDataStoreImpl): RemoteLoginDataStore
}