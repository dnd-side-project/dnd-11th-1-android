package com.materip.core_repository.repositoryDI

import com.materip.core_repository.repository.test_repository.TestRepository
import com.materip.core_repository.repository.test_repository.TestRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindTestRepositoryImpl(testRepository: TestRepositoryImpl): TestRepository
}