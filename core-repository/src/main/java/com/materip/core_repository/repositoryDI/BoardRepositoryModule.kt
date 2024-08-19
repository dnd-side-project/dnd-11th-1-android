package com.materip.core_repository.repositoryDI

import com.materip.core_repository.repository.home_repository.BoardRepository
import com.materip.core_repository.repository.home_repository.BoardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BoardRepositoryModule {
    @Binds
    @Singleton
    fun bindBoardRepositoryImpl(boardRepository: BoardRepositoryImpl): BoardRepository
}