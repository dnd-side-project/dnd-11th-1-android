package com.materip.core_datastore

import com.materip.core_datastore.accompany_datastore.AccompanyDataStore
import com.materip.core_datastore.accompany_datastore.AccompanyDataStoreImpl
import com.materip.core_datastore.com.materip.core_datastore.login_datastore.LocalLoginDataStore
import com.materip.core_datastore.login_datastore.LocalLoginDataStoreImpl
import com.materip.core_datastore.com.materip.core_datastore.login_datastore.RemoteLoginDataStore
import com.materip.core_datastore.com.materip.core_datastore.login_datastore.RemoteLoginDataStoreImpl
import com.materip.core_datastore.com.materip.core_datastore.onboarding_datastore.OnboardingDataStore
import com.materip.core_datastore.com.materip.core_datastore.onboarding_datastore.OnboardingDataStoreImpl
import com.materip.core_datastore.home_datastore.BoardDataStore
import com.materip.core_datastore.home_datastore.BoardDataStoreImpl
import com.materip.core_datastore.image_datastore.ImageDataStore
import com.materip.core_datastore.image_datastore.ImageDataStoreImpl
import com.materip.core_datastore.profile_datastore.ProfileDataStore
import com.materip.core_datastore.profile_datastore.ProfileDataStoreImpl
import com.materip.core_datastore.review_datastore.ReviewDataStore
import com.materip.core_datastore.review_datastore.ReviewDataStoreImpl
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

    @Singleton
    @Binds
    fun provideRemoteOnboardingDataStore(onboardingDataStoreImpl: OnboardingDataStoreImpl): OnboardingDataStore

    @Singleton
    @Binds
    fun provideProfileDataStore(profileDataStoreImpl: ProfileDataStoreImpl): ProfileDataStore

    @Singleton
    @Binds
    fun provideImageDataStore(imageDataStoreImpl: ImageDataStoreImpl): ImageDataStore

    @Singleton
    @Binds
    fun provideAccompanyDataStore(accompanyDataStoreImpl: AccompanyDataStoreImpl): AccompanyDataStore

    @Singleton
    @Binds
    fun provideReviewDataStore(reviewDataStoreImpl: ReviewDataStoreImpl): ReviewDataStore

    @Singleton
    @Binds
    fun provideBoardDataStore(boardDataStoreImpl: BoardDataStoreImpl): BoardDataStore
}