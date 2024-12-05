package com.materip.core_repository.repositoryDI

import com.materip.core_repository.repository.accompany_repository.AccompanyRepository
import com.materip.core_repository.repository.accompany_repository.AccompanyRepositoryImpl
import com.materip.core_repository.repository.home_repository.BoardRepository
import com.materip.core_repository.repository.home_repository.BoardRepositoryImpl
import com.materip.core_repository.repository.image_repository.ImageRepository
import com.materip.core_repository.repository.image_repository.ImageRepositoryImpl
import com.materip.core_repository.repository.login_repository.LoginRepository
import com.materip.core_repository.repository.login_repository.LoginRepositoryImpl
import com.materip.core_repository.repository.onboarding_repository.OnboardingRepository
import com.materip.core_repository.repository.onboarding_repository.OnboardingRepositoryImpl
import com.materip.core_repository.repository.profile_repository.ProfileRepository
import com.materip.core_repository.repository.profile_repository.ProfileRepositoryImpl
import com.materip.core_repository.repository.qna_repository.QnARepository
import com.materip.core_repository.repository.qna_repository.QnARepositoryImpl
import com.materip.core_repository.repository.review_repository.ReviewRepository
import com.materip.core_repository.repository.review_repository.ReviewRepositoryImpl
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

    @Binds
    @Singleton
    fun bindImageRepositoryImpl(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository

    @Binds
    @Singleton
    fun bindBoardRepositoryImpl(boardRepository: BoardRepositoryImpl): BoardRepository

    @Binds
    @Singleton
    fun bindAccompanyRepositoryImpl(accompanyRepositoryImpl: AccompanyRepositoryImpl): AccompanyRepository

    @Binds
    @Singleton
    fun bindReviewRepositoryImpl(reviewRepositoryImpl: ReviewRepositoryImpl): ReviewRepository

    @Binds
    @Singleton
    fun bindQnARepositoryImpl(qnARepositoryImpl: QnARepositoryImpl): QnARepository
}