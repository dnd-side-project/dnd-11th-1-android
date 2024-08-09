package com.materip.matetrip

import com.materip.core_datastore.BoardDataStore
import com.materip.core_datastore.BoardDataStoreImpl
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