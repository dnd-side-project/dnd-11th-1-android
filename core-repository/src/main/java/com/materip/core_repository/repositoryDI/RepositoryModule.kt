package com.materip.core_repository.repositoryDI

import com.materip.core_repository.repository.login_repository.LoginRepository
import com.materip.core_repository.repository.login_repository.LoginRepositoryImpl
import com.materip.core_repository.repository.onboarding_repository.OnboardingRepository
import com.materip.core_repository.repository.onboarding_repository.OnboardingRepositoryImpl
import com.materip.core_repository.repository.profile_repository.ProfileRepository
import com.materip.core_repository.repository.profile_repository.ProfileRepositoryImpl
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

    @Binds
    @Singleton
    fun bindLoginRepositoryImpl(loginRepository: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    fun bindOnboardingRepositoryImpl(onboardingRepositoryImpl: OnboardingRepositoryImpl): OnboardingRepository

    @Binds
    @Singleton
    fun bindProfileRepositoryImpl(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}