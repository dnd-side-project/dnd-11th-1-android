package com.materip.core_datastore.home_datastore

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface BoardDataStoreModule {
    @Singleton
    @Binds
    fun provideBoardDataStore(boardDataStore: BoardDataStoreImpl): BoardDataStore
}